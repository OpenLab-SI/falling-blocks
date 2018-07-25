package si.openlab.falling_blocks;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;

public class FallingBlocks extends Game {
    AssetManager assets;
    int level = 1;

    @Override
    public void create() {
        assets = new AssetManager();

        FileHandleResolver resolver = new InternalFileHandleResolver();
        assets.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assets.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreeTypeFontLoaderParameter gameFontParameter = new FreeTypeFontLoaderParameter();
        gameFontParameter.fontFileName = "whitrabt.ttf";
        gameFontParameter.fontParameters.size = 24;

        FreeTypeFontLoaderParameter titleFontParameter = new FreeTypeFontLoaderParameter();
        titleFontParameter.fontFileName = "whitrabt.ttf";
        titleFontParameter.fontParameters.size = 60;

        // zacne nalagati square.png kot teksturo
        assets.load("title.ttf", BitmapFont.class, titleFontParameter);
        assets.load("game.ttf", BitmapFont.class, gameFontParameter);
        assets.load("square.png", Texture.class);

        setScreen(new LoadingScreen(this));
    }

    @Override
    public void dispose() {
        assets.dispose();
    }
}