<template>
  <div class="container mt-3">
    <h1 class="display-1 text-center">사용자 목록</h1>
    <div class="btn-group">
      <router-link to="/user/save" class="btn btn-primary"
        >사용자 추가</router-link
      >
    </div>
    <table class="table table-hover mt-3">
      <thead class="table-dark">
        <tr>
          <th>이름</th>
          <th>이메일</th>
          <th>가입날짜</th>
        </tr>
      </thead>
      <tbody>
        <tr class="cursor-pointer" onclick="location.href = '/user/findById';">
          <td>스티븐</td>
          <td>jobs@shellfolder.com</td>
          <td>2023-02-27</td>
        </tr>
        <tr class="cursor-pointer" onclick="location.href = '/user/findById';">
          <td>에브릴</td>
          <td>lavign@shellfolder.com</td>
          <td>2023-02-27</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>