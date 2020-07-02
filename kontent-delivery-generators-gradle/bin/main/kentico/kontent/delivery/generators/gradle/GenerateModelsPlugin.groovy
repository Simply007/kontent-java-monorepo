package kentico.kontent.delivery.generators.gradle

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.SourceSet

import java.util.concurrent.Callable

class GenerateModelsPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        GenerateModelsExtension extension =
                project.getExtensions().create("kenticoModel", GenerateModelsExtension.class)

        project.getTasks().create("generateModels", GenerateModels.class)

        project.getPlugins().withType(JavaPlugin.class, new Action<JavaPlugin>() {
            @Override
            void execute(JavaPlugin javaPlugin) {
                JavaPluginConvention javaPluginConvention =
                        project.getConvention().getPlugin(JavaPluginConvention.class)
                SourceSet main = javaPluginConvention.getSourceSets().getByName(SourceSet.MAIN_SOURCE_SET_NAME)
                main.getJava().srcDir(new Callable<File>(){
                    @Override
                    File call() throws Exception {
                        return project.kenticoModel.outputDir
                    }
                })
            }
        })
    }
}
