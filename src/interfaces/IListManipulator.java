package interfaces;

import common.InvalidIndexException;
import common.InvalidListException;
import common.ListNode;

/**
 * Interface for an ADT providing operations on linked lists.
 */
public interface IListManipulator {

  /**
   * Returns the size of a list.
   *
   * @param head the head of the list
   * @return the size of the list
   */
  int size(ListNode head);

  /**
   * Returns a boolean on whether a list is empty.
   *
   * @param head the head of the list
   * @return true if the list is empty, false otherwise.
   */
  boolean isEmpty(ListNode head);

  /**
   * Checks whether a list contains an element equal to the given element.
   *
   * @param head    the head of the list
   * @param element the element to be matched
   * @return true if the list contains an element equal to the given element
   */
  boolean contains(ListNode head, Object element);

  /**
   * Counts the number of occurrences of a given element in a list.
   *
   * @param head    the head of the list
   * @param element the element to be matched
   * @return the number of elements in the list that are equal to the given element
   */
  int count(ListNode head, Object element);

  /**
   * Returns a string representation of a list.
   *
   * @param head the head of the list
   * @return a string representation comprising the string representations of the list elements, separated by commas
   */
  String convertToString(ListNode head);

  /**
   * Accesses an element of a list, counting from the head of the list.
   *
   * @param head the head of the list
   * @param n    the position of the required element, with zero interpreted as the head
   * @return the element at the specified position
   * @throws InvalidIndexException if the position is not valid
   */
  Object getFromFront(ListNode head, int n) throws InvalidIndexException;

  /**
   * Accesses an element of a list, counting back from the tail of the list.
   *
   * @param head the head of the list
   * @param n    the position of the required element, with zero interpreted as the tail
   * @return the element at the specified position
   * @throws InvalidIndexException if the position is not valid
   */
  Object getFromBack(ListNode head, int n) throws InvalidIndexException;

  /**
   * Checks for equality of two lists.
   *
   * @param head1 the head of the first list
   * @param head2 the head of the second list
   * @return true if the lists have equal length and the corresponding elements at each position are equal
   */
  boolean equals(ListNode head1, ListNode head2);

  /**
   * Checks whether a list contains duplicate elements that are equal to each other.
   *
   * @param head the head of the list
   * @return true if the list contains two or more elements that are equal to one another
   */
  boolean containsDuplicates(ListNode head);

  /**
   * Adds a single node to the front of a list. You may assume that the head and node are both non-null and that
   * the node is a single node (i.e. it is not part of a list).
   *
   * @param head the head of the list
   * @param node the new head of the list
   * @return a list where node is the new head of the original list
   */
  ListNode addHead(ListNode head, ListNode node);

  /**
   * Appends one list to the end of another. The resulting list may include parts of the input lists.
   *
   * @param head1 the head of the first list
   * @param head2 the head of the second list
   * @return a list containing the elements of the first list followed by the elements of the second list
   */
  ListNode append(ListNode head1, ListNode head2);

  /**
   * Adds a node into the list at index n.
   *
   * @param head the head of the list
   * @param node the node to be added into the list. You may assume node is a single node (i.e. it is not part of a list)
   * @param n    the index at which a node is to be inserted into
   * @return the original list containing the node at index n
   * @throws InvalidIndexException if the position is not valid
   */
  ListNode insert(ListNode head, ListNode node, int n) throws InvalidIndexException;

  /**
   * Removes the first occurrence of the specified element from this list, if it is present.
   *
   * @param head the head of the list
   * @param elem the element to be removed from the list
   * @return the list with the first occurrence of elem removed
   */
  ListNode delete(ListNode head, Object elem);

  /**
   * Returns the result of reversing the given list.
   *
   * @param head the head of the list
   * @return a list containing the elements of the given list in reverse order
   */
  ListNode reverse(ListNode head);

  /**
   * Splits a non-empty list starting at head at the given node into two non-empty sublists, returning the result as a list containing the two heads, one for each sublist.
   * Splitting the list [5, 3, 9] at 3 should give new list of two sublists, namely [ [5] , [3, 9] ].
   *
   * @param head the head of the list that is to be split
   * @param node the node at which to split the list, this can be assumed to be a valid node in the list
   * @return the split of the list as a list of two non-empty sublists
   * @throws InvalidListException if the given list cannot be split into two non-empty sublists
   */
  ListNode split(ListNode head, ListNode node) throws InvalidListException;

  /**
   * Returns the result of applying the given transformation to all elements of the input list.
   *
   * @param head           the head of the list
   * @param transformation the transformation to be applied to each element
   * @return a list containing the transformed elements
   */
  ListNode map(ListNode head, IMapTransformation transformation);

  /**
   * Returns the result of combining the given initial value and all the elements of the input list using the given associative operator.
   * For example, applying an 'add' operator to a list of integers, using the initial value zero, would produce the sum of the list elements.
   *
   * @param head     the head of the list
   * @param operator the operator used to combine elements
   * @param initial  the initial value to be combined with the first element
   * @return the result of combining the initial value and all the elements in the list
   */
  Object reduce(ListNode head, IReduceOperator operator, Object initial);

  /**
   * Returns the result of filtering a list according to the specified filter condition.
   *
   * @param head      the head of the list
   * @param condition the condition for elements to be included in the filtered list
   * @return a list containing only those elements meeting the specified filter condition in the same order as in the original list
   */
  ListNode filter(ListNode head, IFilterCondition condition);
}
