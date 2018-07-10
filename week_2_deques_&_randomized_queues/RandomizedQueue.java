import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

  private int size;
  private Item[] queue;

  /**
   * construct an empty construct an empty
   */
  public RandomizedQueue() {
    size = 0;
    queue = (Item[]) new Object[1];

  }

  /**
   * resize
   *
   * @param capacity
   */
  private void resize(int capacity) {
    Item[] temp = (Item[]) new Object[capacity];
    for (int i = 0; i < size; i++) {
      temp[i] = queue[i];
    }
    queue = temp;
  }

  /**
   * is the randomized queue empty?
   *
   * @return
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * return the number of items on the randomized queue
   *
   * @return
   */
  public int size() {
    return size;
  }

  /**
   * add the item
   *
   * @param item
   */
  public void enqueue(Item item) {
    if (item == null)
      throw new java.lang.IllegalArgumentException();
    if (size == queue.length) resize(2 * size);
    queue[size++] = item;
  }

  /**
   * remove and return a random item
   *
   * @return
   */
  public Item dequeue() {
    if (size == 0) {
      throw new java.util.NoSuchElementException();
    }
    int random = StdRandom.uniform(size);
    Item item = queue[random];
    if (random !=size-1) queue[random] = queue[--size];
    queue[size] = null;
    //here without n>0. wrong.
    if (size > 0 && size == queue.length / 4) {
      resize(queue.length / 2);
    }
    return item;
  }

  /**
   * return a random item (but do not remove it)
   *
   * @return
   */
  public Item sample() {
    if (size == 0) {
      throw new java.util.NoSuchElementException();
    }
    return queue[StdRandom.uniform(size)];
  }

  /**
   * return an independent iterator over items in random order
   *
   * @return
   */
  public Iterator<Item> iterator() {
    return new IteratorList<>();
  }

  private class IteratorList<Item> implements Iterator<Item> {
    private int index;
    private final Item[] iterArray;

    public IteratorList() {
      index = 0;
      iterArray = (Item[]) new Object[size];
      for (int i = 0; i < size; i++) {
        iterArray[i] = (Item) queue[i];
      }
      StdRandom.shuffle(iterArray);
    }

    @Override
    public boolean hasNext() {
      return index < size;
    }

    @Override
    public Item next() {
      if (!hasNext()) {
        throw new java.util.NoSuchElementException();
      }
      Item item = (Item) iterArray[index++];
      return item;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  /**
   * main method
   *
   * @param args
   */
  public static void main(String[] args) {
    // unit testing (optional)}
    RandomizedQueue<String> randQueue = new RandomizedQueue<>();
    randQueue.enqueue("AA");
    randQueue.enqueue("BB");
    randQueue.enqueue("CC");
    randQueue.dequeue();
    randQueue.dequeue();
    randQueue.enqueue("LL");
    randQueue.dequeue();
    randQueue.dequeue();
    randQueue.dequeue();
    StdOut.println(randQueue.size());
    for (String s : randQueue) {
      StdOut.println(s);
    }
  }
}

