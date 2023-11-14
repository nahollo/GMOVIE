package com.gmovie.gmovie.controller;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.gmovie.gmovie.dto.UserDTO;
import com.gmovie.gmovie.mapper.UserMapper;
import com.gmovie.gmovie.method.Method;
import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse.File;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
public class WordDocumentController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/createWordDocumentAndSendEmail")
    public void createWordDocumentAndSendEmail(@RequestParam String roomId, HttpSession session, HttpServletRequest request,
            HttpServletResponse response, Model model)
            throws IOException {
                int userNo = (Integer)session.getAttribute("userNo");
                System.out.println(userNo);
        UserDTO userDTO = userMapper.findByUserNo(userNo);

        if (userDTO != null) {
            String recipientEmail = userDTO.getEmail();
            System.out.println(recipientEmail);

            // Create a new Word document
            XWPFDocument document = new XWPFDocument();
            XWPFParagraph paragraph = document.createParagraph();

            Method method = new Method();

            String text = method.summary(roomId);

            // Split the text into lines and add them to the paragraph
            String[] lines = text.split("\n");
            for (String line : lines) {
                XWPFRun run = paragraph.createRun();
                run.setText(line);
                run.addBreak(); // Add line break after each line
            }

            // Save the document to a file
            String filePath = roomId + ".docx";
            try {
                FileOutputStream fos = new FileOutputStream(filePath);
                document.write(fos);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Send the email
            try {
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setTo(recipientEmail);
                helper.setSubject(roomId + "회의록");
                helper.setText(roomId + " 회의실의 요약된 회의록입니다.");

                // Add an attachment, converting the file path to a DataSource
                Resource fileResource = new FileSystemResource(filePath);
                helper.addAttachment(roomId + ".docx", new InputStreamSource() {
                    @Override
                    public InputStream getInputStream() throws IOException {
                        return fileResource.getInputStream();
                    }
                });

                javaMailSender.send(message);
                response.sendRedirect("redirect:/summary?text=" + URLEncoder.encode(text, StandardCharsets.UTF_8.toString()));

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            response.sendRedirect("/error");
        }
    }

    @PostMapping("/createWordDocument")
    public void createWordDocument(@RequestBody String text) {
        // 创建一个新的Word文档
        XWPFDocument document = new XWPFDocument();

        // 创建一个段落，并将文本添加到文档中
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.createRun().setText(text);

        // 保存文档到文件或进行其他操作
        // 例如：将文档保存到文件
        try {
            FileOutputStream fos = new FileOutputStream("output.docx");
            document.write(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
