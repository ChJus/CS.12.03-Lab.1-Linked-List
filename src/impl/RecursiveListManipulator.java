package impl;

import common.InvalidIndexException;
import common.InvalidListException;
import common.ListNode;
import interfaces.IFilterCondition;
import interfaces.IListManipulator;
import interfaces.IMapTransformation;
import interfaces.IReduceOperator;

/**
 * This class represents the recursive implementation of the IListManipulator interface.
 */
public class RecursiveListManipulator implements IListManipulator {
  @Override
  public int size(ListNode head) {
    if (head == null) return 0;

    ListNode tail = head.previous;
    tail.next = null;
    int count = 1 + size(head.next);
    tail.next = head;

    return count;
  }

  @Override
  public boolean isEmpty(ListNode head) {
    return head == null;
  }

  @Override
  public boolean contains(ListNode head, Object element) {
    if (head == null) return false;

    ListNode tail = head.previous;
    tail.next = null;
    boolean doesContain = head.element.equals(element) || contains(head.next, element);
    tail.next = head;

    return doesContain;
  }

  @Override
  public int count(ListNode head, Object element) {
    if (head == null) return 0;

    ListNode tail = head.previous;
    tail.next = null;
    int count = (head.element.equals(element) ? 1 : 0) + count(head.next, element);
    tail.next = head;

    return count;
  }

  @Override
  public String convertToString(ListNode head) {
    if (head == null) return "";
    if (head.next == null || head.next == head) return head.element + "";

    ListNode tail = head.previous;
    tail.next = null;
    String result = head.element + "," + convertToString(head.next);
    tail.next = head;

    return result;
  }

  @Override
  public Object getFromFront(ListNode head, int n) throws InvalidIndexException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object getFromBack(ListNode head, int n) throws InvalidIndexException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean equals(ListNode head1, ListNode head2) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean containsDuplicates(ListNode head) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public ListNode addHead(ListNode head, ListNode node) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ListNode append(ListNode head1, ListNode head2) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ListNode insert(ListNode head, ListNode node, int n) throws InvalidIndexException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ListNode delete(ListNode head, Object elem) {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public ListNode reverse(ListNode head) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ListNode split(ListNode head, ListNode node) throws InvalidListException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ListNode map(ListNode head, IMapTransformation transformation) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object reduce(ListNode head, IReduceOperator operator, Object initial) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ListNode filter(ListNode head, IFilterCondition condition) {
    // TODO Auto-generated method stub
    return null;
  }

}
