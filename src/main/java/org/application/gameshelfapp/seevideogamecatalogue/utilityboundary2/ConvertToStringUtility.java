package org.application.gameshelfapp.seevideogamecatalogue.utilityboundary2;

import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;

import java.util.Map;

public class ConvertToStringUtility {
    private ConvertToStringUtility(){}

    public static String catalogueBeanToString(SellingGamesCatalogueBean catalogueBean){
        StringBuilder game = new StringBuilder();
        for(VideogameBean gameBean: catalogueBean.getSellingGamesBean()){
            game.append(String.format("name: %s, console: %s, category: %s, copies: %d, price: %f€, description: %s%n", gameBean.getName(), gameBean.getPlatformBean(), gameBean.getCategoryBean(), gameBean.getCopiesBean(), gameBean.getPriceBean(), gameBean.getDescriptionBean())) ;
        }
        return String.valueOf(game);
    }

    public static String mapToString(Map<String, String[]> map) {
        StringBuilder filters = new StringBuilder();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            filters.append(entry.getKey()).append(": ").append(String.join(", ", entry.getValue())).append("\n");
        }
        return filters + "\nType <search, gameTitle, console, category>\n\n";
    }
}
