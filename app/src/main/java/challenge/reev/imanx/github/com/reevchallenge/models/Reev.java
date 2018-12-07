package challenge.reev.imanx.github.com.reevchallenge.models;

import com.google.gson.annotations.SerializedName;

import java.io.StringReader;
import java.util.List;

/**
 * Created by ImanX.
 * ReevChallenge | Copyrights 2018 ZarinPal Crop.
 */
public class Reev {

    @SerializedName("start_point")
    public  Position       startPosition;
    public  List<Position> weirs;
    private String         command;


    public char[] getCommands() {
        return command.toCharArray();
    }

    public class Position {
        public int    x;
        public int    y;

        public String  getCaption(){
            return String.format("(%s,%s)", x, y);
        }
    }
}
