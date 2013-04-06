package org.deman.generator

import groovy.json.JsonSlurper
import org.deman.generator.tag.MyTagLib
import org.deman.generator.templateengine.TemplateEngine
import org.deman.generator.tools.FS;
/**
 * Created with IntelliJ IDEA.
 * User: deman
 * Date: 04/04/13
 * Time: 21:14
 * To change this template use File | Settings | File Templates.
 */
class Generator {

    static void main(String ... args){
        Generator g = new Generator();
        g.generate("templates/model.json","templates/rules.json")
    }

    def generate(def modelsFileName,def rulesFileName){
        def jsonReader = new JsonSlurper();

        // Read files
        def rules = jsonReader.parseText(new File(rulesFileName).text)
        def models = jsonReader.parseText(new File(modelsFileName).text)

        copyPhase(rules);
        templatingPhase(rules,models)
    }

    def copyPhase(rules){
        println("==== COPY PHASE ====")
        rules?.copy?.each {
            println("Copy : $it.input to $it.output")
            FS.copy(it.input,it.output)
        };
    }


    def templatingPhase(rules,models){
        println("==== TEMPLATING PHASE ====")
        println "Register macros."
        def macros = rules?.macros.collectEntries {k,v -> [k , Class.forName(v).newInstance()]}

        rules?.templates?.each { rule ->
            applyTemplate(rule,models,macros)
        };
    }

    def applyTemplate(rule,models,macros){
        def engine = new TemplateEngine()
        def templateFileName = rule.template
        Eval.x(models,rule.model).each { model ->
            def bindings = [:]
            bindings.macro = macros
            bindings.model = model
            bindings.data = [:]
            bindings.data += rule?.data?.collectEntries {k,v -> [k , Eval.x(models,v)]}
            def fileName = engine.directTemplate(rule.output,bindings)
            println "Generating : $fileName"
            def txt = engine.directTemplate(new File(templateFileName).text,bindings)
            FS.createFile(fileName,txt)
        }
    }
}
