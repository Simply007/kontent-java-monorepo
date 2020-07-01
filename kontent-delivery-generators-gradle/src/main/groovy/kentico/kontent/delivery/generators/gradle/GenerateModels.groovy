package kentico.kontent.delivery.generators.gradle

import com.squareup.javapoet.JavaFile
import kentico.kontent.delivery.DeliveryClient
import kentico.kontent.delivery.generators.CodeGenerator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class GenerateModels extends DefaultTask {

    GenerateModels() {
        setDescription("Generates source files from your Kentico Kontent project.")
         // setGroup("KenticoKontent") // TODO uncomment before release https://plugins.gradle.org/docs/submit
    }

    @TaskAction
    void perform() throws IOException {
        String projectId = project.kenticoModel.projectId
        String packageName = project.kenticoModel.packageName
        File outputDir = project.kenticoModel.outputDir
        DeliveryClient deliveryClient = project.kenticoModel.deliveryClient
        CodeGenerator codeGenerator = new CodeGenerator(projectId, packageName, outputDir)
        List<JavaFile> sources =
                deliveryClient == null ? codeGenerator.generateSources() : codeGenerator.generateSources(deliveryClient)
        codeGenerator.writeSources(sources)
    }
}
