# Kentico Kontent model generator for Java

## Get started

TODO


### Run as a gradle task

Add to your `build.gradle` 

```groovy

import com.squareup.javapoet.JavaFile
import kentico.kontent.delivery.DeliveryClient
import kentico.kontent.delivery.DeliveryOptions
import kentico.kontent.delivery.generators.CodeGenerator

buildscript {
    repositories {
        // maven {
        //     url "https://dl.bintray.com/simply007/kontent-java-monorepo"
        // }
        jcenter()
    }
    dependencies {
        classpath('com.github.simply007:kontent-delivery-generators:0.0.2-beta.11')
    }
}

repositories {
    mavenCentral()

    // maven {
    //    url "https://dl.bintray.com/simply007/kontent-java-monorepo"
    // }
    jcenter()

}


dependencies {
    // to be able to use models in your app properly
    implementation 'com.github.simply007:kontent-delivery:0.0.2-beta.11'
}

// showcase task
task generateModels {
    doLast {
        
        // The most complex solution, you could configure the client as you want
        // i.e. set preview API key 
        DeliveryOptions options = new DeliveryOptions();
        options.setProjectId("975bf280-fd91-488c-994c-2f04416e5ee3");
        DeliveryClient client = new DeliveryClient(options);

        CodeGenerator generator = new CodeGenerator(
            options.getProjectId(),
            'com.simply007.test.springapp.models', 
            file('src/main/java')
        );
        List<JavaFile> sources = generator.generateSources(client);
        generator.writeSources(sources);
    }
}

``` 