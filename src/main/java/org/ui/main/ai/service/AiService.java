package org.ui.main.ai.service;


import org.springframework.stereotype.Service;
import org.ui.main.advert.model.Advert;
import org.ui.main.ai.dto.AiRequest;
import org.ui.main.search.dto.PageResponse;
import org.ui.main.search.service.SearchService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class AiService {
    private final SearchService searchService;

    public AiService(SearchService searchService) {
        this.searchService = searchService;
    }

    public PageResponse<Advert> getAdvertByAI(AiRequest query) {

        String response = runPythonScript(query.text());
        HashMap<String, List<String>> map = new HashMap<>();
        System.out.println("response = " + response);
        String[] split = response.split("&");
        for (String s : split) {
            String[] pair = s.split("=");
            List<String> value = Arrays.stream(pair[1].split(",")).toList();
            map.put(pair[0], value);
        }

        return searchService.filterSearchForm(map);
    }

    public String runPythonScript(String text) {
        StringBuilder result = new StringBuilder();
        StringBuilder error = new StringBuilder();
        try {
            String currentDir = System.getProperty("user.dir");
            String pythonInterpreter = Paths.get(currentDir, "scripts", "venv", "bin", "python3").toString();
            String scriptPath = Paths.get(currentDir, "scripts", "generate_query.py").toString();

            ProcessBuilder pb = new ProcessBuilder(pythonInterpreter, scriptPath, text);
            Process p = pb.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }

            BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((line = err.readLine()) != null) {
                error.append(line);
            }

            int exitCode = p.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Python script exited with code " + exitCode + ". Errors: " + error.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
