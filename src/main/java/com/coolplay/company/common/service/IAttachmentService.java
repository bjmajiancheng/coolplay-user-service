package com.coolplay.company.common.service;

import com.coolplay.company.common.baseservice.IBaseService;
import com.coolplay.company.core.model.Attachment;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by chenguojun on 8/28/16.
 */
public interface IAttachmentService extends IBaseService<Attachment> {

    Attachment uploadFileAttachement(HttpServletRequest request, MultipartFile file, String dirName, long maxSize,
            HashMap<String, String> extLimitMap, Integer suffix) throws IOException;
}
