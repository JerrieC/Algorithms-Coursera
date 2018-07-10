

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
  private int size;
  private Node firstNode;
  private Node lastNode;

  /**
   * construct an empty deque
   */
  public Deque() {
    firstNode = null;
    lastNode = null;
    size = 0;
  }

  /**
   * is the deque empty?
   *
   * @return
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * return the number of items on the deque
   *
   * @return
   */
  public int size() {
    return size;
  }

  /**
   * add the item to the front
   *
   * @param item
   */
  public void addFirst(Item item) {
    if (item == null) {
      throw new java.lang.IllegalArgumentException();
    }
    Node newNode = new Node(item, null, firstNode);
    if (size != 0) {
      firstNode.pre = newNode;
    }
    else {
      lastNode = newNode;
    }
    firstNode = newNode;
    size++;
  }

  /**
   * add the item to the end
   *
   * @param item
   */
  public void addLast(Item item) {
    if (item == null) {
      throw new java.lang.IllegalArgumentException();
    }
    Node newNode = new Node(item, lastNode, null);
    if (size != 0) {
      lastNode.next = newNode;
    } else {
      firstNode = newNode;
    }
    lastNode = newNode;
    size++;
  }

  /**
   * remove and return the item from the front
   *
   * @return
   */
  public Item removeFirst() {
    if (size == 0) {
      throw new java.util.NoSuchElementException();
    }

    Item item = firstNode.item;
    firstNode = firstNode.next;
    size--;
    if (size == 0) {
      lastNode = null;
    } else {
      firstNode.pre = null;
    }
    return item;
  }

  /**
   * remove and return the item from the end
   *
   * @return
   */
  public Item removeLast() {
    if (size == 0) {
      throw new java.util.NoSuchElementException();
    }
    Item item = lastNode.item;
    lastNode = lastNode.pre;
    size--;
    if (size == 0) {
      firstNode = null;
    } else {
      lastNode.next = null;
    }
    return item;

  }

  /**
   * return an iterator over items in order from front to end
   *
   * @return
   */
  public Iterator<Item> iterator() {
    Iterator<Item> itemIterator = new IteratorList();
    return itemIterator;
  }

  private class IteratorList implements Iterator<Item> {
    private Node curNode;

    public IteratorList() {
      this.curNode = firstNode;
    }

    @Override
    public void remove() {
      throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public boolean hasNext() {
      return curNode != null;
    }

    @Override
    public Item next() {
      if (curNode == null) {
        throw new java.util.NoSuchElementException();
      }
      Item item = curNode.item;
      curNode = curNode.next;
      return item;
    }
  }

  private class Node {
    private final Item item;  // 8 bytes
    private Node next;  // 8 bytes
    private Node pre;

    public Node(Item item, Node pre, Node next) {
      this.item = item;
      this.next = next;
      this.pre = pre;
    }
  }

  /**
   * main test
   *
   * @param args
   */
  public static void main(String[] args) {
    // unit testing
    Deque<String> deque = new Deque<>();
    deque.addFirst("AA");
    StdOut.println(deque.size());
    deque.addFirst("BB");
    deque.addLast("BB");
    deque.addLast("CC");
    for (String s : deque) {
      StdOut.println(s);
    }
    deque.removeFirst();
    deque.removeLast();
    deque.removeLast();
    deque.removeFirst();
  }
}

