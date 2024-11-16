package com.physmo.garnetexamples.games.cellsurvivor;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.physmo.garnet.FileUtils;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.graphics.Texture;
import com.physmo.garnet.graphics.TileSheet;
import com.physmo.garnet.text.RegularFont;
import com.physmo.garnetexamples.games.cellsurvivor.gamedata.GameData;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class Resources {
    public static final String regularFontImagePath = "regularfonts/drake_10x10.png";
    String spritesFileName = "cellsurvivor.png";
    Texture spritesTexture;
    TileSheet spritesTilesheet;
    Graphics graphics;
    RegularFont regularFont;
    Document dataDocument;
    GameData gameData;

    public GameData getGameData() {
        return gameData;
    }

    public void init(Graphics graphics) {
        if (this.graphics != null) return;

        this.graphics = graphics;
        spritesTexture = Texture.loadTexture(spritesFileName);
        spritesTexture.setFilter(false); // Show what smoothed textures look like
        graphics.addTexture(spritesTexture);
        spritesTilesheet = new TileSheet(spritesTexture, 16, 16);

        // Regular font
        regularFont = new RegularFont(regularFontImagePath, 10, 10);

        try {
            readDataFile();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public void readDataFile() throws ParserConfigurationException, IOException, SAXException {
        InputStream inputStream = FileUtils.getFileFromResourceAsStream("cellsurvivor/game_data.xml");
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        dataDocument = builder.parse(inputStream);
        dataDocument.getDocumentElement().normalize();

        // Try jackson instead
        InputStream inputStream2 = FileUtils.getFileFromResourceAsStream("cellsurvivor/game_data.xml");
        XmlMapper xmlMapper = new XmlMapper();
        gameData = xmlMapper.readValue(inputStream2, GameData.class);

        System.out.println(gameData);
    }

    public void getEnemyData() {
        NodeList nodeList = dataDocument.getElementsByTagName("enemy");
    }

    public Texture getSpritesTexture() {
        return spritesTexture;
    }

    public TileSheet getSpritesTilesheet() {
        return spritesTilesheet;
    }

    public RegularFont getRegularFont() {
        return regularFont;
    }


}
