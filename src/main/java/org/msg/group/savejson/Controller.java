package org.msg.group.savejson;

import org.springframework.web.bind.annotation.*;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

//import com.fasterxml.jackson;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@RestController
@RequestMapping("/saveJSON")
public class Controller {

    private static final String FILE_NAME = "C:\\poze";

    @RequestMapping(value = "file", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public List<String> save(@RequestBody FileWrapper wrapper) {
        List<String> response = new ArrayList<String>();
        for (File file : wrapper.getFiles()) {
            String pathOfDownload = file.getPath();
            try (BufferedInputStream in = new BufferedInputStream(new URL(pathOfDownload).openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME)) {
                byte dataBuffer[] = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStream in = null;
            try {
                in = new URL(pathOfDownload).openStream();
                Files.copy(in, Paths.get(FILE_NAME), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return response;
    }

}
