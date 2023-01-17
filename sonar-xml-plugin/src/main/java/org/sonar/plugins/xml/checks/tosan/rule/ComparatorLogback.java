/*
 * SonarQube XML Plugin
 * Copyright (C) 2010-2021 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.sonar.plugins.xml.checks.tosan.rule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ComparatorLogback {
    private static final String APP_NAME="app_name";
    private static final String APP_VERSION="app_version";
    private static final String RAW_LOG="raw_log";
    private List<String> addressList = new ArrayList();
    private final String fileName="logback.xml";
    private List<String> blackList=Arrays.asList(new String[]{"target", "values"});

    public ComparatorLogback() {

    }

//    private List<String> getListLogback(String path) {
//
//        File searchPath = new File(path);
//        File[] listFiles = searchPath.listFiles();
//        for (File file : listFiles) {
//            if (file.isDirectory()) {
//                if(!blackList.contains(file.getName()))
//                    getListLogback(file.getAbsolutePath());
//
//            } else {
//                if (file.getName().matches(fileName)) {
//                    addressList.add(file.getAbsolutePath());
//                }
//            }
//        }
//        return addressList;
//    }


    public void comparePattern(String path) throws Exception {
        SaxReader logstashReader = new SaxReader();
        ObjectMapper objectPattern = new ObjectMapper();
//        StringBuilder listProblemLogbackFiles=new StringBuilder();

//        for (String logbackAddress : getListLogback(path)) {
//            String logstashPatternFile = logstashReader.getLogstashPattern(logbackAddress);
        String logstashPatternFile=null;

        try {
            logstashPatternFile  = logstashReader.getLogstashPattern(path);

        }catch (Exception e){
            throw new IllegalStateException("LOGSTASG appender dose not exist in this logback.xml");
        }
              if (!logstashPatternFile.isEmpty()) {
                  String Pattern = logstashPatternFile.toLowerCase();
                  HashMap logstashPattern = objectPattern.readValue(Pattern, HashMap.class);

                  if (logstashPattern.containsKey(APP_NAME) && logstashPattern.containsKey(APP_VERSION) && logstashPattern.containsKey(RAW_LOG)) {

                  } else {
//                      throw new IllegalStateException(logstashPatternFile);
                                              throw new IllegalStateException("The LOGSTASH pattern not contain {app_name , app_version , rwa_log}");
                  }

              }

//        catch (IllegalStateException e) {
//            throw new IllegalStateException("the logbak.xml is empty");
//        }
//            try {
//                                            if (!logstashPatternFile.equals("null")) {
//                                                String Pattern = logstashPatternFile.toLowerCase();
//                                                HashMap logstashPattern = objectPattern.readValue(Pattern, HashMap.class);
//                                                if (logstashPattern.containsKey(APP_NAME) && logstashPattern.containsKey(APP_VERSION) && logstashPattern.containsKey(RAW_LOG)) {
//
//                                                } else {
//                                                    throw new IllegalStateException("The LOGSTASH pattern not contain {app_name , app_version , rwa_log}");
//
//                            //                        listProblemLogbackFiles.append("address is : "+path);
//                            //                        listProblemLogbackFiles.append("address is : "+logbackAddress+"<br>");
//                                                }
//                                            } else {
//                                                throw new IllegalStateException("The LOGSTASH appender dont exist");
//                            //                    listProblemLogbackFiles.append("address is : "+path);
//                            //                    listProblemLogbackFiles.append("address is : "+logbackAddress+"<br>");
//                                            }
//            } catch (JsonProcessingException e) {
//            }
//        }
//        return listProblemLogbackFiles.toString();
//        return logstashPatternFile;
    }}
