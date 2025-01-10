package impl;

import common.InvalidIndexException;
import common.InvalidListException;
import common.ListNode;
import interfaces.IFilterCondition;
import interfaces.IListManipulator;
import interfaces.IMapTransformation;
import interfaces.IReduceOperator;

import java.util.ArrayList;

/**
 * This class represents the iterative implementation of the IListManipulator interface.
 */
public class ListManipulator implements IListManipulator {
  @Override
  public int size(ListNode head) {
    if (head == null) return 0;

    ListNode currentNode = head;
    int count = 1;

    while (currentNode.next != head) {
      currentNode = currentNode.next;
      count++;
    }
    return count;
  }

  @Override
  public boolean isEmpty(ListNode head) {
    return head == null;
  }

  @Override
  public boolean contains(ListNode head, Object element) {
    if (head == null) return false;
    if (head.element.equals(element)) return true;

    ListNode currentNode = head;
    while (currentNode.next != head) {
      currentNode = currentNode.next;
      if (currentNode.element.equals(element)) return true;
    }
    return false;
  }

  @Override
  public int count(ListNode head, Object element) {
    if (head == null || element == null) return 0;

    ListNode currentNode = head;
    int count = head.element.equals(element) ? 1 : 0;

    while (currentNode.next != head) {
      currentNode = currentNode.next;
      if (currentNode.element.equals(element)) count++;
    }
    return count;
  }

  @Override
  public String convertToString(ListNode head) {
    if (head == null) return "";

    ListNode currentNode = head;
    String result = currentNode.element + "";
    while (currentNode.next != head) {
      currentNode = currentNode.next;
      result += "," + currentNode.element;
    }

    return result;
  }

  @Override
  public Object getFromFront(ListNode head, int n) throws InvalidIndexException {
    if (n < 0 || head == null) throw new InvalidIndexException();

    ListNode currentNode = head;
    while (currentNode.next != head && n > 0) {
      currentNode = currentNode.next;
      n--;
    }

    if (n != 0) throw new InvalidIndexException();
    return currentNode.element;
  }

  @Override
  public Object getFromBack(ListNode head, int n) throws InvalidIndexException {
    return getFromFront(head, size(head) - n - 1);
  }

  @Override
  public boolean equals(ListNode firstNode, ListNode secondNode) {
    if (firstNode == null && secondNode == null) return true;
    if (firstNode == null || secondNode == null) return false;
    if (size(firstNode) != size(secondNode)) return false;

    try {
      for (int i = 0; i < size(firstNode); i++) {
        if (!getFromFront(firstNode, i).equals(getFromFront(secondNode, i))) {
          return false;
        }
      }
    } catch (InvalidIndexException e) {
      return false;
    }

    return true;
  }

  @Override
  public boolean containsDuplicates(ListNode head) {
    if (head == null) return false;

    ArrayList<Object> uniqueItems = new ArrayList<>();
    ListNode currentNode = head;
    do {
      if (uniqueItems.contains(currentNode.element)) return true;
      uniqueItems.add(currentNode.element);
      currentNode = currentNode.next;
    } while (currentNode != head);

    return false;
  }

  @Override
  public ListNode addHead(ListNode head, ListNode node) {
    ListNode tail = head.previous;
    tail.next = node;
    node.previous = tail;
    node.next = head;
    head.previous = node;
    return node;
  }

  @Override
  public ListNode append(ListNode firstListHead, ListNode secondListHead) {
    if (firstListHead == null && secondListHead == null) return null;
    if (firstListHead == null) return secondListHead;
    if (secondListHead == null) return firstListHead;


    firstListHead.previous.next = secondListHead;
    secondListHead.previous.next = firstListHead;
    secondListHead.previous = firstListHead.previous;
    firstListHead.previous = secondListHead;

    return firstListHead;
  }

  public ListNode insert(ListNode head, ListNode node, int n) throws InvalidIndexException {
    if (n < 0 || n > size(head)) throw new InvalidIndexException();
    if (n == 0) return addHead(head, node);
    if (n == size(head)) return append(head, node);

    ListNode currentNode = head;

    while (n != 1) {
      currentNode = currentNode.next;
      n--;
    }

    node.next = currentNode.next;
    node.previous = currentNode;
    currentNode.next = node;

    return head;
  }

  public ListNode delete(ListNode head, Object elem) {
    if (head.element.equals(elem) && size(head) == 1) return null;
    if (!contains(head, elem)) return head;

    ListNode currentNode = head;
    while (currentNode.next != head && !currentNode.element.equals(elem)) {
      currentNode = currentNode.next;
    }

    if (currentNode.element.equals(elem)) {
      if (currentNode.equals(head)) {
        ListNode tail = head.previous;
        ListNode newHead = head.next;
        newHead.previous = tail;
        tail.next = newHead;
        return newHead;
      } else {
        currentNode.previous.next = currentNode.next;
        currentNode.next.previous = currentNode.previous;
      }
    }

    return head;
  }

  @Override
  public ListNode reverse(ListNode head) {
    if (head == null || size(head) == 1) return head;

    ListNode newHead = head.previous;
    ListNode currentNode = head;
    do {
      ListNode temp = currentNode.next;
      currentNode.next = currentNode.previous;
      currentNode.previous = temp;
      currentNode = currentNode.next;
    } while (currentNode != head);

    return newHead;
  }

  @Override
  public ListNode split(ListNode head, ListNode node) throws InvalidListException {
    if (head == null || node == null || head.element.equals(node.element))
      throw new InvalidListException();

    ListNode currentNode = head;
    do {
      if (currentNode.element.equals(node.element)) {
        ListNode tail = head.previous;
        currentNode.previous.next = head;
        head.previous = currentNode.previous;
        currentNode.previous = tail;
        tail.next = currentNode;

        ListNode result = new ListNode(head);
        result.next = new ListNode(currentNode);
        result.previous = result.next;
        result.next.next = result;
        result.next.previous = result;

        return result;
      }
      currentNode = currentNode.next;
    } while (currentNode != head);

    return head;
  }

  @Override
  public ListNode map(ListNode head, IMapTransformation transformation) {
    if (head == null) return null;

    ListNode currentNode = head;
    do {
      currentNode.element = transformation.transform(currentNode.element);
      currentNode = currentNode.next;
    } while (currentNode != head);

    return head;
  }

  @Override
  public Object reduce(ListNode head, IReduceOperator operator, Object initial) {
    if (head == null) return initial;

    ListNode currentNode = head;
    Object result = initial;
    do {
      result = operator.operate(result, currentNode.element);
      currentNode = currentNode.next;
    } while (currentNode != head);

    return result;
  }

  @Override
  public ListNode filter(ListNode head, IFilterCondition condition) {
    if (head == null) return null;

    ListNode currentNode = head.previous;
    do {
      if (!condition.isSatisfied(currentNode.element)) {
        if (size(head) == 1) {
          return null;
        }
        head = delete(head, currentNode.element);
      }
      currentNode = currentNode.previous;
    } while (currentNode != head || !condition.isSatisfied(currentNode.element));

    return head;
  }
}
