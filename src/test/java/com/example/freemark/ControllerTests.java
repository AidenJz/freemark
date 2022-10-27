package com.example.freemark;

import com.example.freemark.controller.Freemarker2PDFController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ControllerTests {

    @Resource
    private Freemarker2PDFController controller;

    @Test
    public void testPDF(){




    }
}
