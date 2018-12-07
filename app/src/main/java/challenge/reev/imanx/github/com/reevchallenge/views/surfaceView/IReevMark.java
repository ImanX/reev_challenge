package challenge.reev.imanx.github.com.reevchallenge.views.surfaceView;

import java.util.List;

import challenge.reev.imanx.github.com.reevchallenge.models.Reev;

/**
 * Created by ImanX.
 * ReevChallenge | Copyrights 2018 ZarinPal Crop.
 */
public interface IReevMark {
    void mark(Reev.Position position, char c);

    void mark(Reev.Position position);

    void start(Reev.Position startPosition, List<Reev.Position> weirs, OnDoCommandListener listener);

    void clean(Reev.Position position);

}
