package org.deman.generator.templateengine

import groovy.text.GStringTemplateEngine
import groovy.text.Template

import java.security.MessageDigest

/**
 * Created with IntelliJ IDEA.
 * User: deman
 * Date: 04/04/13
 * Time: 22:32
 * To change this template use File | Settings | File Templates.
 */
class TemplateEngine {
    static def ENGINE = new GStringTemplateEngine()
    Map<String,Template> templates = [:]

    TemplateEngine register(String name,String text){
        templates[name]=ENGINE.createTemplate(text)
        this
    }

    TemplateEngine register(String name,File file){
        templates[name]=ENGINE.createTemplate(file)
        this
    }

    TemplateEngine register(String name,InputStream stream){
        templates[name]=ENGINE.createTemplate(stream.getText())
        this
    }

    TemplateEngine registerFile(String name,String fileName){
        register(name,new File(fileName))
        this
    }

    Closure template(String name){
        return { Map<String,Object> bindings ->
            templates[name].make(bindings).toString()
        }
    }

    def directTemplate(String text,bindings){
        ENGINE.createTemplate(text).make(bindings).toString()
    }
}
