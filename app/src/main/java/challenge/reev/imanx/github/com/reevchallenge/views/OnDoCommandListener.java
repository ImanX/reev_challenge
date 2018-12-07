package challenge.reev.imanx.github.com.reevchallenge.views;

import challenge.reev.imanx.github.com.reevchallenge.models.Reev;

/**
 * Created by ImanX.
 * ReevChallenge | Copyrights 2018 ZarinPal Crop.
 */
public interface OnDoCommandListener {
    void onMove(char moveCommand, Reev.Position position);

    void onCrash(Reev.Position position);

}
