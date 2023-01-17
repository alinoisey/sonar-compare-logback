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
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;

public class SaxReader {


    public SaxReader() {

    }


    public String getLogstashPattern(String path) throws Exception{
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        File input = new File(path);
        SaxReaderFileHandler standardHandler=null;
try {
    SAXParser saxParser = saxParserFactory.newSAXParser();
//            SaxReaderFileHandler standardHandler = new SaxReaderFileHandler();
     standardHandler = new SaxReaderFileHandler();
    saxParser.parse(input, standardHandler);
//        } catch (Exception e) {
//            throw new SAXException();
//        }
        if(standardHandler.pattern.isEmpty()){
            throw new SAXException("dose not exist logstash appender");
        }

        }catch (SAXException e){
            throw new SAXException("this is ============");
        }
        return standardHandler.pattern;

    }

        //        return "null";
    }

//-----------------------------------------------------------------------------------------


    class SaxReaderFileHandler extends DefaultHandler {
        boolean isPattern = false;
        String logstash = null;
        String pattern = null;

        @Override
        public void startElement(String uri,String localName, String qName, Attributes attributes) throws SAXException{

            if (qName.equalsIgnoreCase("appender")) {
                String keyword = attributes.getValue("class");
                if (keyword.equalsIgnoreCase("net.logstash.logback.appender.LogstashTcpSocketAppender")) {
                    logstash = keyword;
                }
            } else if (qName.equalsIgnoreCase("pattern")) {
                if (logstash != null) {
                    isPattern = true;
                }
            }
        }

        @Override
        public void endElement(String uri,String localName, String qName) throws SAXException {
            if (qName.equalsIgnoreCase("appender")) {
                logstash = null;
            }
        }

        @Override
        public void characters(char ch[],int start, int length) throws SAXException {
            if (isPattern) {
                if (logstash != null) {
                    pattern = new String(ch, start, length);
                    isPattern = false;
                }
            }

        }
    }

