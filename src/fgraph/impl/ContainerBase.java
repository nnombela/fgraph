package fgraph.impl;

import fgraph.Container;
import fgraph.GraphFactory;
import fgraph.GraphObject;

/**
 * Created by IntelliJ IDEA.
 * User: nnombela
 * Date: 28/05/11
 * Time: 19:11
 * To change this template use File | Settings | File Templates.
 */
public class ContainerBase<G extends GraphObject> implements Container<G> {
    protected GraphObject[] objs = new GraphObject[1];
    protected int size = 0;
    protected GraphObject belongsTo;
    protected Type type;

    public ContainerBase(GraphObject belongsTo, Type type) {
        this.belongsTo = belongsTo;
        this.type = type;
    }


    public boolean is(Type type) {
        return this.type == type;
    }

    public boolean is(Family family) {
        return belongsTo().is(family);
    }

    public Type type() {
        return type;
    }

    public Family[] families() {
        return belongsTo.families();
    }

    public GraphFactory factory() {
        return belongsTo.factory();
    }

    public void free() {
        forEach(new Closure<G>() {
            public void execute(G g) {
                g.free();
            }
        });
    }

    public GraphObject belongsTo() {
        return belongsTo;
    }

    public G get(int index) {
        return (G) objs[index];
    }

    protected void set(G g, int index) {
        objs[index] = g;
    }

    protected void reserve(int capacity) {
        if (objs.length < capacity) {
            GraphObject[] newObjs = new GraphObject[capacity];
            System.arraycopy(objs, 0, newObjs, 0, size);
            objs = newObjs;
        }
    }

    protected void accommodate() {
        if (objs.length == size()) {
            reserve((3 * (objs.length + 1)) / 2);
        }
    }

    public Iterator<G> iterator() {
        return new Iterator<G>() {
            private int cursor = -1;

            public G next() {
                return get(++cursor);
            }

            public boolean hasNext() {
                return cursor + 1 < size();
            }
        };
    }

    public Accessor<G> accessor() {
        return new Accessor<G>() {
            private Object[] objs = new Object[size()];

            public void set(G g, Object obj) {
                objs[index(g)] = obj;
            }

            public Object get(G g) {
                return objs[index(g)];
            }

            public Object[] objects() {
                Object[] array = new Object[objs.length];
                System.arraycopy(objs, 0, array, 0, objs.length);
                return array;
            }
        };
    }

    public void forEach(Closure<G> closure) {
        for(Iterator<G> iterator = iterator(); iterator.hasNext();) {
            closure.execute(iterator.next());
        }
    }

    public G find(Condition<G> condition) {
        for(Iterator<G> iterator = iterator(); iterator.hasNext();) {
            G g = iterator.next();
            if (condition.check(g)) {
                return g;
            }
        }
        return null;
    }

    public int index(G g) {
        for(int i = 0; i < size(); ++i) {
            if (get(i) == g) {
                return i;
            }
        }
        return -1;
    }

    public int size() {
        return size;
    }

    public boolean contains(G g) {
        return index(g) != -1;
    }

    public G add(G g) {
        accommodate();
        set(g, size++);
        return g;
    }

    public G addNew() {
        return add((G)factory().create(type));
    }

    public void remove(G g) {
        set(get(--size), index(g));
        objs[size] = null;
    }

    public void swap(G g1, G g2) {
        int index1 = index(g1);
        int index2 = index(g2);
        objs[index1] = g2;
        objs[index2] = g1;
    }


}
