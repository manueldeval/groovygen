package org.deman.generator.tag

import org.deman.generator.templateengine.TemplateEngine

/**
 * Created with IntelliJ IDEA.
 * User: deman
 * Date: 05/04/13
 * Time: 20:31
 * To change this template use File | Settings | File Templates.
 */
class MyTagLib {

    TemplateEngine engine;

    MyTagLib(){
        this.engine = new TemplateEngine().register("hello",'Hello $who');
    }

    def hello = { params ->
        engine.template('hello')(params)
    }
}
