package com.gmovie.gmovie.controller;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import java.io.InputStream;

import com.gmovie.gmovie.dto.UserDTO;
import com.gmovie.gmovie.mapper.UserMapper;
import com.gmovie.gmovie.method.Method;
import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse.File;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class WordDocumentController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/createWordDocumentAndSendEmail")
    public String createWordDocumentAndSendEmail(@RequestParam String roomId, HttpServletRequest request)
            throws IOException {
        UserDTO userDTO = userMapper.findByUserNo(1);

        if (userDTO != null) {
            String recipientEmail = userDTO.getEmail();
            System.out.println(recipientEmail);

            // Create a new Word document
            XWPFDocument document = new XWPFDocument();
            XWPFParagraph paragraph = document.createParagraph();

            Method method = new Method();

            String text = method.summary(roomId);

            paragraph.createRun().setText(text);

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
                helper.setSubject("Word Document Attached");
                helper.setText("Please find the attached Word document.");

                // Add an attachment, converting the file path to a DataSource
                Resource fileResource = new FileSystemResource(filePath);
                helper.addAttachment("document.docx", new InputStreamSource() {
                    @Override
                    public InputStream getInputStream() throws IOException {
                        return fileResource.getInputStream();
                    }
                });

                javaMailSender.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "redirect:/summary";
        } else {
            return "redirect:/error"; 
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
