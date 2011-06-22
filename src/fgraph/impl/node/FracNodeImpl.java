package fgraph.impl.node;

import fgraph.*;
import fgraph.impl.Checker;

/**
 * This class models...
 * Author: nnombela@gmail.com
 * Date: 8/06/11
 */
public class FracNodeImpl extends NodeImpl {
    protected Graph down;
    protected Halfe up;

    @Override
    public GraphFactory factory() {
        return GraphFactory.get("fractal-graph");
    }

    public FracNodeImpl() {
        this(new HalfesImpl());
    }

    public FracNodeImpl(Halfes halfes) {
        super(halfes);
    }


    public void free() {
        free(up);
        free(down);
        super.free();
    }

    @Override
    public Graph down() {
        return down;
    }

    @Override
    public Halfe up() {
        return up;
    }

    @Override
    public void setDown(Graph down) {
        this.down = Checker.setDown(this, down);
    }
    @Override
    public void setUp(Halfe up) {
        this.up = Checker.setUp(this, up);
    }
}
