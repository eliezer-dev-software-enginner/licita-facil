package my_app;

import megalodonte.ListenerManager;
import megalodonte.application.Context;
import megalodonte.application.MegalodonteApp;
import megalodonte.base.RouteProps;
import megalodonte.router.v2.Router;
import my_app.hotreload.HotReload;

import java.util.Set;

public class Main {
    static HotReload hotReload;
    static boolean devMode = false;

    public static JsonDB jsonDB = new JsonDB();

    static void main() {
        MegalodonteApp.run(context -> {
            final var stage = context.javafxStage();
            stage.setTitle("licita-facil por Eliezer Dev");
            stage.setWidth(900);
            stage.setHeight(650);

            var routes = Set.of(
                    new Router.Route("home", router -> new HomeScreen(router),
                            new RouteProps(900, 550,null, false)),
                    //ok
                    new Router.Route("screen-b",router-> new FornecedoresScreen(router),
                            new RouteProps(1000, 650, "Tela B", true))
            );

            var router = new Router(routes, "home");

            context.useRouter(router);
            context.useView(router.entrypoint().view());



            //initialize(context);
            initialize(router, context);

            MegalodonteApp.onShutdown(() -> {
                System.out.println("Clicked on X - close application");
                hotReload.stop();
                ListenerManager.disposeAll();
            });
        });
    }

    //public static void initialize(Context context) {
    public static void initialize(Router router, Context context) {
        //context.useView(new HomeScreen().render());

        context.useView(router.entrypoint().view());
        if (devMode) {
           hotReload = new HotReload()
                .sourcePath("src/main/java")
                .classesPath("build/classes/java/main")
                .resourcesPath("src/main/resources")
                .implementationClassName("my_app.hotreload.UIReloaderImpl")
                .screenClassName("my_app.HomeScreen")
                .reloadContext(context)
                .classesToExclude(Set.of(
                    "my_app.Main",
                    "my_app.hotreload.Reloader",
                    "my_app.hotreload.UIReloaderImpl",
                    "my_app.hotreload.HotReload",
                    "my_app.hotreload.HotReloadClassLoader"
                ));
           hotReload.start();
        }
    }
}