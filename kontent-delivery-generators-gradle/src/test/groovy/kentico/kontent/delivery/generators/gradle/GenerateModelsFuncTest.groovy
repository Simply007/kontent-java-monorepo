package kentico.kontent.delivery.generators.gradle

import org.apache.http.HttpHost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.bootstrap.ServerBootstrap
import org.apache.http.localserver.LocalServerTestBase
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class GenerateModelsFuncTest extends Specification {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder()
    File buildFile
    File javaSrcFile
    File javaTestFile
    def localServer = new LocalServer()

    def setup() {
        buildFile = temporaryFolder.newFile('build.gradle')
        javaSrcFile =
                new File(temporaryFolder.newFolder("src", "main", "java", "com", "dancinggoat"),
                        "Main.java")
        javaTestFile =
                new File(temporaryFolder.newFolder("src", "test", "java", "com", "dancinggoat"),
                        "MainTest.java")
        javaSrcFile.createNewFile()
        javaTestFile.createNewFile()
        localServer.setUp()
    }

    def cleanup() {
        localServer.shutDown()
    }

    def "test that when the plugin is applied to a project and the generateModels task is executed, the sources are generated successfully, and can be compiled with other sources"() {
        setup:
        String projectId = "02a70003-e864-464e-b62c-e0ede97deb8c"

        localServer.getProtectedServerBootstrap()
                .registerHandler(
                        String.format("/%s/%s", projectId, "types"),
                        { request, response, context ->
                            return response.setEntity(
                                    new StringEntity("{\"types\":[{\"system\":{\"id\":\"b7aa4a53-d9b1-48cf-b7a6-ed0b182c4b89\",\"name\":\"Article\",\"codename\":\"article\",\"last_modified\":\"2017-04-04T13:40:29.970377Z\"},\"elements\":{\"personas\":{\"type\":\"taxonomy\",\"name\":\"Personas\",\"taxonomy_group\":\"personas\"},\"title\":{\"type\":\"text\",\"name\":\"Title\"},\"teaser_image\":{\"type\":\"asset\",\"name\":\"Teaserimage\"},\"post_date\":{\"type\":\"date_time\",\"name\":\"Postdate\"},\"summary\":{\"type\":\"text\",\"name\":\"Summary\"},\"body_copy\":{\"type\":\"rich_text\",\"name\":\"BodyCopy\"},\"related_articles\":{\"type\":\"modular_content\",\"name\":\"Relatedarticles\"},\"meta_keywords\":{\"type\":\"text\",\"name\":\"Metakeywords\"},\"meta_description\":{\"type\":\"text\",\"name\":\"Metadescription\"}}},{\"system\":{\"id\":\"7bc932b3-ce2a-4aa7-954e-04cbcbd214fc\",\"name\":\"Brewer\",\"codename\":\"brewer\",\"last_modified\":\"2017-04-04T13:40:29.970377Z\"},\"elements\":{\"product_name\":{\"type\":\"text\",\"name\":\"Productname\"},\"price\":{\"type\":\"number\",\"name\":\"Price\"},\"image\":{\"type\":\"asset\",\"name\":\"Image\"},\"product_status\":{\"type\":\"taxonomy\",\"name\":\"Productstatus\",\"taxonomy_group\":\"product_status\"},\"manufacturer\":{\"type\":\"text\",\"name\":\"Manufacturer\"},\"in_stock\":{\"type\":\"multiple_choice\",\"name\":\"InStock\"},\"short_description\":{\"type\":\"rich_text\",\"name\":\"Shortdescription\"},\"long_description\":{\"type\":\"rich_text\",\"name\":\"Longdescription\"}}}],\"pagination\":{\"skip\":0,\"limit\":3,\"count\":3,\"next_page\":\"https://kenticodeliver-apiwebapp.azurewebsites.net/975bf280-fd91-488c-994c-2f04416e5ee3/types?limit=3&skip=3\"}}")
//                                    new InputStreamEntity(
//                                            this.getClass().getResourceAsStream("SampleContentTypeList.json")
//                                    )
                            )
                        })

        HttpHost httpHost = localServer.start()

        buildFile << '''
            plugins {
                id 'com.github.simply007.kontent.delivery.generator'
            }
            
            apply plugin: 'java'
            
            repositories {
                mavenCentral()
            }

        '''

        buildFile << "\n"
        buildFile << "//This is sort of a hack, but ensuring test hits our mocked server\n"
        buildFile << "import kentico.kontent.delivery.*\n"
        buildFile << "DeliveryOptions deliveryOptions = new DeliveryOptions()\n"
        buildFile << "deliveryOptions.setProductionEndpoint(\"${httpHost.toURI()}\")\n"
        buildFile << "deliveryOptions.setProjectId(\"${projectId}\")\n"
        buildFile << "DeliveryClient client = new DeliveryClient(deliveryOptions)\n"

        buildFile << '''
            kenticoModel {
                projectId = '02a70003-e864-464e-b62c-e0ede97deb8c'
                packageName = 'com.dancinggoat.models'
                outputDir = file('generated-sources')
                deliveryClient = client
            }
            
            dependencies {
        '''

        buildFile << "compile fileTree(dir: '${new File(System.getProperty("user.dir")).parentFile.toString().replace('\\', '/')}/kontent-delivery/build/libs', include: 'kontent-delivery-*.jar')"

        buildFile << '''
                compile fileTree(dir: '${project.rootDir}/kontent-delivery/build/libs', include: 'kontent-delivery-*.jar')
                testCompile group: 'junit', name: 'junit', version: '4.12'
            }
        '''

        javaSrcFile << '''
        package com.dancinggoat;
        
        import com.dancinggoat.models.Article;
        import com.dancinggoat.models.Brewer;
        
        public class Main {
            public static Article getNewArticle() { return new Article(); }
            public static Brewer getNewBrewer() { return new Brewer(); }
        }
        '''

        javaTestFile << '''
        package com.dancinggoat;
        
        import org.junit.Assert;
        import org.junit.Test;
        
        public class MainTest {
            @Test
            public void test() {
                Assert.assertNotNull(Main.getNewArticle());
                Assert.assertNotNull(Main.getNewBrewer());
            }
        }
        '''
        when:
        GradleRunner runner = GradleRunner.create()
                .withProjectDir(temporaryFolder.getRoot())
                .withArguments('generateModels', 'build', '--stacktrace', '--refresh-dependencies')
                .withPluginClasspath()
        BuildResult result = runner.build()
        then:
        result.task(':generateModels').getOutcome() == TaskOutcome.SUCCESS
        new File(temporaryFolder.getRoot(), "generated-sources/com/dancinggoat/models/Article.java").exists()
        new File(temporaryFolder.getRoot(), "generated-sources/com/dancinggoat/models/Brewer.java").exists()
        new File(temporaryFolder.getRoot(), "generated-sources/com/dancinggoat/models").listFiles().length == 2
    }

    class LocalServer extends LocalServerTestBase {
        ServerBootstrap getProtectedServerBootstrap() {
            return this.serverBootstrap
        }
    }
}
