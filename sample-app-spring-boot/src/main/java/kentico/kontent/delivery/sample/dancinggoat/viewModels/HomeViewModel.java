package kentico.kontent.delivery.sample.dancinggoat.viewModels;

import kentico.kontent.delivery.sample.dancinggoat.models.HeroUnit;
import kentico.kontent.delivery.sample.dancinggoat.models.Home;

public class HomeViewModel {
    public BannerViewModel banner;
    public LayoutViewModel layout;

    public HomeViewModel(Home model) {
        this.layout = new LayoutViewModel();
        this.layout.title = model.getMetadataTwitterSite();

        this.banner = new BannerViewModel(model.getHeroUnit().get(0).castTo(HeroUnit.class));
    }
}
