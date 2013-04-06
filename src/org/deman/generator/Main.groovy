package org.deman.generator

/**
 * Created with IntelliJ IDEA.
 * User: deman
 * Date: 06/04/13
 * Time: 19:24
 * To change this template use File | Settings | File Templates.
 */
class Main {

    static void main(String ... args){
        if (args.length == 0){
            println ("Usage : model.json rules.json");
            System.exit(1)
        }
        Generator generator = new Generator()
        generator.generate(args[0],args[0])
    }
}
