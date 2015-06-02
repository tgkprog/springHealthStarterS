package com.sel2in.smsWebSend.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;

@Controller
@RequestMapping(value = "/prescription")
public class PrescriptionController {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(PrescriptionController.class.getName());

}
