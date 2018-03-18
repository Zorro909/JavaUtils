package javautils.UtilHelpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Cleanable<T> {

  private static Thread               cleaner;
  private static ArrayList<Cleanable> cleanables = new ArrayList<Cleanable>();

  long                     lastUsed;
  long                     cleanTime;
  long                     maxCleanTime;
  long                     created;
  T                        object;
  ArrayList<Collection<T>> collections = new ArrayList<Collection<T>>();
  ArrayList<Map>           maps        = new ArrayList<Map>();


  public Cleanable(T o, long cleanTime) {
    this(o,cleanTime,Long.MAX_VALUE - System.currentTimeMillis() - 1000);
  }

  public Cleanable(T o, long cleanTime, long maxCleanTime) {

    object = o;
    lastUsed = System.currentTimeMillis();
    this.cleanTime = cleanTime;
    created = System.currentTimeMillis();
    this.maxCleanTime = maxCleanTime;
    cleanables.add(this);

    if (cleaner == null) {
      cleaner = new Thread(new Runnable() {

        @Override
        public void run() {
          while (!cleanables.isEmpty()) {
            for (Iterator<Cleanable> iterator = cleanables.iterator(); iterator.hasNext();) {
              Cleanable c = iterator.next();
              synchronized (c) {
                if (c.lastUsed + c.cleanTime < System.currentTimeMillis() || c.maxCleanTime + c.created < System.currentTimeMillis() ) {
                  c.removeAll();
                  iterator.remove();
                }
              }
            }
            try {
              Thread.sleep(1000L);
            } catch (InterruptedException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
          cleaner = null;
        }

      });
      cleaner.start();
    }
  }

  public <K> void addToMap(K key, Map<K, Cleanable<T>> map) {
    maps.add(map);
    map.put(key, this);
  }

  public void addToList(Collection<Cleanable<T>> c) {
    c.add(this);
  }

  public void setObject(T o) {
    object = o;
    cleanables.add(this);
  }

  public T getObject() {
    lastUsed = System.currentTimeMillis();
    return object;
  }

  protected void removeAll() {
    for (Collection c : collections) {
      c.remove(this);
    }
    for (Map m : maps) {
      m.values().remove(this);
    }
    object = null;
  }

}
