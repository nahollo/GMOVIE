const calendar = document.querySelector(".calendar"),
  date = document.querySelector(".date"),
  daysContainer = document.querySelector(".days"),
  prev = document.querySelector(".prev"),
  next = document.querySelector(".next"),
  todayBtn = document.querySelector(".today-btn"),
  gotoBtn = document.querySelector(".goto-btn"),
  dateInput = document.querySelector(".date-input"),
  eventDay = document.querySelector(".event-day"),
  eventDate = document.querySelector(".event-date"),
  eventsContainer = document.querySelector(".events"),
  addEventBtn = document.querySelector(".add-event"),
  addEventWrapper = document.querySelector(".add-event-wrapper "),
  addEventCloseBtn = document.querySelector(".close "),
  addEventTitle = document.querySelector(".event-name "),
  addEventFrom = document.querySelector(".event-time-from "),
  addEventTo = document.querySelector(".event-time-to "),
  addEventSubmit = document.querySelector(".add-event-btn "),

  buttonContainer = document.querySelector(".vertical-container"),
  mypageButton = document.getElementById("mypageButton"),
  addGroupButton = document.getElementById("addNewFunctionButton"),
  shareContainer = document.querySelector(".share-container"),
  deleteContainer = document.querySelector(".delete-container");


let today = new Date();
let activeDay;
let month = today.getMonth();
let year = today.getFullYear();
const storedUser = parseInt(sessionStorage.getItem("userNo"), 10); // 將其解析為數字

mypageButton.addEventListener("click", function (event) {
  event.stopPropagation();
  if (shareButton) {
    shareButton.remove();
  }
  if (deleteButton) {
    deleteButton.remove();
  }
  window.location.reload();
  eventsArr.length = 0;
  getEvents(storedUser);
  window.location.reload();
});

addGroupButton.addEventListener("click", function (event) {
  event.stopPropagation();
  // 使用prompt函数获取用户输入的按钮名称
  const groupName = prompt("이름을 입력하세요");
  const groupId = generateUniqueId()

  const group = {
    id: groupId,
    name: groupName,
  };

  if (groupName) { // 确保用户输入了名称
    // 创建一个新按钮元素
    const newButton = document.createElement("button");
    newButton.textContent = groupName;
    newButton.className = "custom-button";
    buttonContainer.insertBefore(newButton, addGroupButton);
    saveGroup(group);
  }



});

let shareButton = null;
let deleteButton = null;
let buttonName;
buttonContainer.addEventListener("click", function (event) {
  const target = event.target;

  if (target.tagName === "BUTTON") {
    console.log("按钮被点击了");
    buttonName = target.textContent;
    const buttonId = target.getAttribute('group-id');
    eventsArr.length = 0;
    getGroupEvents(buttonId);

    if (shareButton) {
      shareButton.remove();
    }
    if (deleteButton) {
      deleteButton.remove();
    }
    shareButton = document.createElement("button");
    shareButton.textContent = "Share";
    shareButton.className = "share-button";
    shareButton.setAttribute('group-id', buttonId);

    deleteButton = document.createElement("button");
    deleteButton.className = "delete-button";
    deleteButton.textContent = "Delete";
    deleteButton.setAttribute('group-id', buttonId);


    shareContainer.appendChild(shareButton);
    deleteContainer.appendChild(deleteButton);

    shareButton.addEventListener("click", function () {
      // 在这里执行 "share" 按钮的操作
      const groupId = shareButton.getAttribute('group-id');
      console.log("Share 按钮被点击了",groupId);

      const userEmail = prompt(buttonName+"에 추가할 사용자의 이메일 입력하세요");

      if (userEmail) {
        fetch(`/api/calendar/addUserToGroup/${userEmail}`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({ id: groupId }),
        })
          .then(response => {
            if (response.ok) {
              console.log('사용자 추가하였습니다.');
            } else {
              console.error('사용자 존제하지 않습니다.');
            }
          })
          .catch(error => {
            console.error('에러：', error);
          });
      }
    });
  }
});

// 删除 group 的函数
function deleteGroup(groupId) {
  fetch(`/api/calendar/deleteGroup/${groupId}`, {
    method: 'DELETE',
  })
    .then(response => {
      if (response.ok) {
        return response.text();
      } else {
        throw new Error('Failed to delete the group.');
      }
    })
    .then(() => {
      // 删除成功后重新加载页面
      setTimeout(() => {
        window.location.reload();

      }, 3000);
      
    })
    .catch(error => {
      console.error('삭제 에러：', error);
    });
}

// 监听按钮容器的点击事件
deleteContainer.addEventListener("click", function (event) {
  const target = event.target;

  if (target.textContent.startsWith("Delete")) {
    // 如果点击的是 "Delete" 按钮
    if (confirm(buttonName+"삭제하겠습니까？")) {
      // 用户确认删除
      const buttonId = target.getAttribute('group-id');
      deleteGroup(buttonId);
    }
  }
});




function getGroupEvents(groupId) {
  fetch(`/api/calendar/groupevents/${groupId}`)
    .then(response => {
      if (response.ok) {
        return response.json();
      } else {
        console.log('No events found for this group.');
        initCalendar();
        return;
      }
    })
    .then(data => {
      // Create a dictionary to group events by date

      const eventsByDate = {};
      data.forEach(event => {
        const key = `${event.year}-${event.month}-${event.day}`;
        if (!eventsByDate[key]) {
          eventsByDate[key] = {
            day: event.day,
            month: event.month,
            year: event.year,
            events: []
          };
        }
        eventsByDate[key].events.push({
          title: event.title,
          time: event.time,
          id: event.id
        });
      });

      // Convert the dictionary into an array of events
      eventsArr.length = 0; // Clear existing data
      eventsArr.push(...Object.values(eventsByDate));
      initCalendar(); // Update the calendar
    })
    .catch(error => {
      console.error('Error fetching events:', error);
    });
}



function saveGroup(group) {
  // 修改事件格式以匹配后端的期望
  const formattedEvent = {
    id: group.id,
    name: group.name
  };
  const membership = {
    id: group.id,
    user: storedUser
  };

  fetch('/api/calendar/groups', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(formattedEvent),
  })
    .then(response => {
      if (response.ok) {
        return response.json();
      } else {
        throw new Error('Failed to save group.');
      }
    })
    .then(data => {
      console.log('Group saved successfully:', data);
      saveMembership(membership);
    })
    .catch(error => {
      console.error('Error saving event:', error);
    });
}

function saveMembership(membership) {
  // 修改事件格式以匹配后端的期望
  const formattedEvent = {
    id: membership.id,
    user: membership.user
  };

  fetch('/api/calendar/membership', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(formattedEvent),
  })
    .then(response => {
      if (response.ok) {
        return response.json();
      } else {
        throw new Error('Failed to save membership.');
      }
    })
    .then(data => {
      console.log('Membership saved successfully:', data);
      setTimeout(() => {
        window.location.reload();

      }, 300);
    })
    .catch(error => {
      console.error('Error saving event:', error);
    });
}
// Function to get groups from Oracle
function getGroup(storedUser) {
  fetch(`/api/calendar/groups/${storedUser}`)
    .then(response => {
      if (response.ok) {
        return response.json();
      } else {
        throw new Error('Failed to fetch groups.');
      }
    })
    .then(data => {
      data.forEach(group => {
        const newButton = document.createElement('button');
        newButton.textContent = group.name;
        newButton.setAttribute('group-id', group.id);
        newButton.className = "custom-button";
        buttonContainer.insertBefore(newButton, addGroupButton);
      });

    })
    .catch(error => {
      console.error('Error fetching :', error);
    });
}








const months = [
  "January",
  "February",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December",
];

// const eventsArr = [
//   {
//     day: 13,
//     month: 11,
//     year: 2022,
//     events: [
//       {
//         title: "Event 1 lorem ipsun dolar sit genfa tersd dsad ",
//         time: "10:00 AM",
//       },
//       {
//         title: "Event 2",
//         time: "11:00 AM",
//       },
//     ],
//   },
// ];

const eventsArr = [];
getEvents(storedUser);
getGroup(storedUser);
console.log(eventsArr);

//function to add days in days with class day and prev-date next-date on previous month and next month days and active on today
function initCalendar() {
  const firstDay = new Date(year, month, 1);
  const lastDay = new Date(year, month + 1, 0);
  const prevLastDay = new Date(year, month, 0);
  const prevDays = prevLastDay.getDate();
  const lastDate = lastDay.getDate();
  const day = firstDay.getDay();
  const nextDays = 7 - lastDay.getDay() - 1;

  date.innerHTML = months[month] + " " + year;

  let days = "";

  for (let x = day; x > 0; x--) {
    days += `<div class="day prev-date">${prevDays - x + 1}</div>`;
  }

  for (let i = 1; i <= lastDate; i++) {
    //check if event is present on that day
    let event = false;
    eventsArr.forEach((eventObj) => {
      if (
        eventObj.day === i &&
        eventObj.month === month + 1 &&
        eventObj.year === year
      ) {
        event = true;
      }
    });
    if (
      i === new Date().getDate() &&
      year === new Date().getFullYear() &&
      month === new Date().getMonth()
    ) {
      activeDay = i;
      getActiveDay(i);
      updateEvents(i);
      if (event) {
        days += `<div class="day today active event">${i}</div>`;
      } else {
        days += `<div class="day today active">${i}</div>`;
      }
    } else {
      if (event) {
        days += `<div class="day event">${i}</div>`;
      } else {
        days += `<div class="day ">${i}</div>`;
      }
    }
  }

  for (let j = 1; j <= nextDays; j++) {
    days += `<div class="day next-date">${j}</div>`;
  }
  daysContainer.innerHTML = days;
  addListner();
}

//function to add month and year on prev and next button
function prevMonth() {
  month--;
  if (month < 0) {
    month = 11;
    year--;
  }
  initCalendar();
}

function nextMonth() {
  month++;
  if (month > 11) {
    month = 0;
    year++;
  }
  initCalendar();
}

prev.addEventListener("click", prevMonth);
next.addEventListener("click", nextMonth);

initCalendar();

//function to add active on day
function addListner() {
  const days = document.querySelectorAll(".day");
  days.forEach((day) => {
    day.addEventListener("click", (e) => {
      getActiveDay(e.target.innerHTML);
      updateEvents(Number(e.target.innerHTML));
      activeDay = Number(e.target.innerHTML);
      //remove active
      days.forEach((day) => {
        day.classList.remove("active");
      });
      //if clicked prev-date or next-date switch to that month
      if (e.target.classList.contains("prev-date")) {
        prevMonth();
        //add active to clicked day afte month is change
        setTimeout(() => {
          //add active where no prev-date or next-date
          const days = document.querySelectorAll(".day");
          days.forEach((day) => {
            if (
              !day.classList.contains("prev-date") &&
              day.innerHTML === e.target.innerHTML
            ) {
              day.classList.add("active");
            }
          });
        }, 100);
      } else if (e.target.classList.contains("next-date")) {
        nextMonth();
        //add active to clicked day afte month is changed
        setTimeout(() => {
          const days = document.querySelectorAll(".day");
          days.forEach((day) => {
            if (
              !day.classList.contains("next-date") &&
              day.innerHTML === e.target.innerHTML
            ) {
              day.classList.add("active");
            }
          });
        }, 100);
      } else {
        e.target.classList.add("active");
      }
    });
  });
}

todayBtn.addEventListener("click", () => {
  today = new Date();
  month = today.getMonth();
  year = today.getFullYear();
  initCalendar();
});

dateInput.addEventListener("input", (e) => {
  dateInput.value = dateInput.value.replace(/[^0-9/]/g, "");
  if (dateInput.value.length === 2) {
    dateInput.value += "/";
  }
  if (dateInput.value.length > 7) {
    dateInput.value = dateInput.value.slice(0, 7);
  }
  if (e.inputType === "deleteContentBackward") {
    if (dateInput.value.length === 3) {
      dateInput.value = dateInput.value.slice(0, 2);
    }
  }
});

gotoBtn.addEventListener("click", gotoDate);

function gotoDate() {
  console.log("here");
  const dateArr = dateInput.value.split("/");
  if (dateArr.length === 2) {
    if (dateArr[0] > 0 && dateArr[0] < 13 && dateArr[1].length === 4) {
      month = dateArr[0] - 1;
      year = dateArr[1];
      initCalendar();
      return;
    }
  }
  alert("Invalid Date");
}

//function get active day day name and date and update eventday eventdate
function getActiveDay(date) {
  const day = new Date(year, month, date);
  const dayName = day.toString().split(" ")[0];
  eventDay.innerHTML = dayName;
  eventDate.innerHTML = date + " " + months[month] + " " + year;
}

//function update events when a day is active
function updateEvents(date) {
  let events = "";
  eventsArr.forEach((event) => {
    if (
      date === event.day &&
      month + 1 === event.month &&
      year === event.year
    ) {
      event.events.forEach((event) => {
        events += `<div class="event" data-event-id="${event.id}">
            <div class="title">
              <i class="fas fa-circle"></i>
              <h3 class="event-title">${event.title}</h3>
            </div>
            <div class="event-time">
              <span class="event-time">${event.time}</span>
            </div>
        </div>`;
      });
    }
  });
  if (events === "") {
    events = `<div class="no-event">
            <h3>No Events</h3>
        </div>`;
  }
  eventsContainer.innerHTML = events;
  saveEvents();
}

//function to add event
addEventBtn.addEventListener("click", () => {
  addEventWrapper.classList.toggle("active");
});

addEventCloseBtn.addEventListener("click", () => {
  addEventWrapper.classList.remove("active");
});

document.addEventListener("click", (e) => {
  if (e.target !== addEventBtn && !addEventWrapper.contains(e.target)) {
    addEventWrapper.classList.remove("active");
  }
});

//allow 50 chars in eventtitle
addEventTitle.addEventListener("input", (e) => {
  addEventTitle.value = addEventTitle.value.slice(0, 60);
});


//allow only time in eventtime from and to
addEventFrom.addEventListener("input", (e) => {
  addEventFrom.value = addEventFrom.value.replace(/[^0-9:]/g, "");
  if (addEventFrom.value.length === 2) {
    addEventFrom.value += ":";
  }
  if (addEventFrom.value.length > 5) {
    addEventFrom.value = addEventFrom.value.slice(0, 5);
  }
});

addEventTo.addEventListener("input", (e) => {
  addEventTo.value = addEventTo.value.replace(/[^0-9:]/g, "");
  if (addEventTo.value.length === 2) {
    addEventTo.value += ":";
  }
  if (addEventTo.value.length > 5) {
    addEventTo.value = addEventTo.value.slice(0, 5);
  }
});

//function to add event to eventsArr
addEventSubmit.addEventListener("click", () => {
  const eventTitle = addEventTitle.value;
  const eventTimeFrom = addEventFrom.value;
  const eventTimeTo = addEventTo.value;
  const eventId = generateUniqueId();
  if (eventTitle === "" || eventTimeFrom === "" || eventTimeTo === "") {
    alert("Please fill all the fields");
    return;
  }

  //check correct time format 24 hour
  const timeFromArr = eventTimeFrom.split(":");
  const timeToArr = eventTimeTo.split(":");
  if (
    timeFromArr.length !== 2 ||
    timeToArr.length !== 2 ||
    timeFromArr[0] > 23 ||
    timeFromArr[1] > 59 ||
    timeToArr[0] > 23 ||
    timeToArr[1] > 59
  ) {
    alert("Invalid Time Format");
    return;
  }

  const timeFrom = convertTime(eventTimeFrom);
  const timeTo = convertTime(eventTimeTo);

  //check if event is already added
  let eventExist = false;
  eventsArr.forEach((event) => {
    if (
      event.day === activeDay &&
      event.month === month + 1 &&
      event.year === year
    ) {
      event.events.forEach((event) => {
        if (event.title === eventTitle) {
          eventExist = true;
        }
      });
    }
  });
  if (eventExist) {
    alert("Event already added");
    return;
  }
  const newEvent = {
    id: eventId,
    title: eventTitle,
    time: timeFrom + " - " + timeTo,
  };
  console.log(newEvent);
  console.log(activeDay);
  let eventAdded = false;
  if (eventsArr.length > 0) {
    eventsArr.forEach((item) => {
      if (
        item.day === activeDay &&
        item.month === month + 1 &&
        item.year === year
      ) {
        item.events.push(newEvent);
        eventAdded = true;
      }
    });
  }

  if (!eventAdded) {
    eventsArr.push({
      userno: storedUser,
      day: activeDay,
      month: month + 1,
      year: year,
      events: [newEvent],
    });
  }

  console.log(eventsArr);
  addEventWrapper.classList.remove("active");
  addEventTitle.value = "";
  addEventFrom.value = "";
  addEventTo.value = "";
  updateEvents(activeDay);
  //select active day and add event class if not added
  const activeDayEl = document.querySelector(".day.active");
  if (!activeDayEl.classList.contains("event")) {
    activeDayEl.classList.add("event");
  }

  let shareButtonId = null;
  if (shareButton) {
    shareButtonId = shareButton.getAttribute('group-id');
    console.log(shareButtonId);
  }

  // 创建事件对象
  const event = {
    userno: storedUser,
    id: eventId, // 事件的唯一标识，通常为空（由数据库生成）
    title: eventTitle,
    day: activeDay,
    month: month + 1,
    year: year,
    time: timeFrom + " - " + timeTo,  // 日期时间格式
    groupId: shareButtonId,
  };

  saveEventsToServer(event);
});

// 저장 

function saveEventsToServer(event) {
  // 修改事件格式以匹配后端的期望
  const formattedEvent = {
    userno: event.userno,
    id: event.id,
    title: event.title,
    day: event.day,
    month: event.month,
    year: event.year,
    time: event.time,
    groupId: event.groupId
  };

  fetch('/api/calendar/events', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(formattedEvent),
  })
    .then(response => {
      if (response.ok) {
        return response.json();
      } else {
        throw new Error('Failed to save event.');
      }
    })
    .then(data => {
      console.log('Event saved successfully:', data);
    })
    .catch(error => {
      console.error('Error saving event:', error);
    });
}

// //function to delete event when clicked on event
// eventsContainer.addEventListener("click", (e) => {
//   if (e.target.classList.contains("event")) {
//     if (confirm("Are you sure you want to delete this event?")) {
//       const eventTitle = e.target.children[0].children[1].innerHTML;
//       eventsArr.forEach((event) => {
//         if (
//           event.day === activeDay &&
//           event.month === month + 1 &&
//           event.year === year
//         ) {
//           event.events.forEach((item, index) => {
//            if (item.title === eventTitle) {
//              event.events.splice(index, 1);
//            }
//          });
//           //if no events left in a day then remove that day from eventsArr
//          if (event.events.length === 0) {
//            eventsArr.splice(eventsArr.indexOf(event), 1);
//            //remove event class from day
//            const activeDayEl = document.querySelector(".day.active");
//            if (activeDayEl.classList.contains("event")) {
//              activeDayEl.classList.remove("event");
//            }
//          }
//        }
//      });
//      updateEvents(activeDay);
//    }
//  }
// });

// Function to delete an event by ID
function deleteEventById(eventId) {
  // Send a DELETE request with the event ID to delete
  fetch(`/api/calendar/events/${eventId}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'text/plain',
    }
  })
    .then(response => {
      if (response.ok) {
        return response.text(); // 获取文本响应
      } else {
        throw new Error('Failed to delete the event.');
      }
    })
    .then(data => {
      // 删除成功后执行更新界面的操作
      eventsArr.forEach((event) => {
        if (
          event.day === activeDay &&
          event.month === month + 1 &&
          event.year === year
        ) {
          event.events.forEach((item, index) => {
            if (item.id === eventId) {
              event.events.splice(index, 1);
            }
          });
          //if no events left in a day then remove that day from eventsArr
          if (event.events.length === 0) {
            eventsArr.splice(eventsArr.indexOf(event), 1);
            //remove event class from day
            const activeDayEl = document.querySelector(".day.active");
            if (activeDayEl.classList.contains("event")) {
              activeDayEl.classList.remove("event");
            }
          }
        }
      });
      updateEvents(activeDay);
    })
    .catch(error => {
      console.error('Error deleting event:', error);
    });
}


// Event listener to delete event when clicked
eventsContainer.addEventListener("click", (e) => {
  if (e.target.classList.contains("event")) {
    if (confirm("Are you sure you want to delete this event?")) {
      // Get the event ID
      const eventId = e.target.dataset.eventId;

      // Ensure an event ID is present
      if (eventId) {
        deleteEventById(eventId);
      } else {
        console.error("Event ID not found.");
      }
    }
  }
});





//function to save events in local storage
function saveEvents() {
  localStorage.setItem("events", JSON.stringify(eventsArr));
}

////function to get events from local storage
//function getEvents() {
////check if events are already saved in local storage then return event else nothing
//if (localStorage.getItem("events") === null) {
//return;
//}
//eventsArr.push(...JSON.parse(localStorage.getItem("events")));
//}

// Function to get events from Oracle
function getEvents(storedUser) {
  fetch(`/api/calendar/events/${storedUser}`)
    .then(response => {
      if (response.ok) {
        return response.json();
      } else {
        throw new Error('Failed to fetch events.');
      }
    })
    .then(data => {
      // Create a dictionary to group events by date
      const eventsByDate = {};
      data.forEach(event => {
        const key = `${event.year}-${event.month}-${event.day}`;
        if (!eventsByDate[key]) {
          eventsByDate[key] = {
            day: event.day,
            month: event.month,
            year: event.year,
            events: []
          };
        }
        eventsByDate[key].events.push({
          title: event.title,
          time: event.time,
          id: event.id
        });
      });

      // Convert the dictionary into an array of events
      eventsArr.length = 0; // Clear existing data
      eventsArr.push(...Object.values(eventsByDate));
      initCalendar(); // Update the calendar
    })
    .catch(error => {
      console.error('Error fetching events:', error);
    });
}



function convertTime(time) {
  //convert time to 24 hour format
  let timeArr = time.split(":");
  let timeHour = timeArr[0];
  let timeMin = timeArr[1];
  let timeFormat = timeHour >= 12 ? "PM" : "AM";
  timeHour = timeHour % 12 || 12;
  time = timeHour + ":" + timeMin + " " + timeFormat;
  return time;
}


function generateUniqueId() {
  const randomStr = Math.random().toString(36).split('.')[1];
  return randomStr.slice(0, 9);
}

