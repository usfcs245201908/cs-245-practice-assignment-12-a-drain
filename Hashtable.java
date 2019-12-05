import java.util.ArrayList;

class Hashtable {

  private class HashNode {
    public String key;
    public String value;
    public HashNode next;
    HashNode(String key, String value){
      this.key = key;
      this.value = value;
      this.next = null;
    }
  }

  ArrayList<HashNode> map;
  private final double LOAD_THRESHOLD = 0.7;
  int entries;
  int buckets;

  Hashtable(){
    this.buckets = 2029;
    this.entries = 0;
    this.map = new ArrayList<HashNode>();
    for(int i=0; i < buckets; i++){
      map.add(null);
    }
  }

  private int getHash(String key){
    return Math.abs(key.hashCode() % buckets);
  }

  String get(String key){
    HashNode head = map.get(getHash(key));

    while(head != null){
      if(head.key == key){
        return head.value;
      }
      head = head.next;
    }
    return null;
  }

  void put(String key, String value){
    HashNode head = map.get(getHash(key));
    HashNode temp = head;
    if(head == null){
      map.set(getHash(key), new HashNode(key, value));
    } else {
      while(temp != null){
        if(temp.key == key){
          temp.value = value;
          return;
        }
        temp = temp.next;
      }

      HashNode node = new HashNode(key, value);
      node.next = head;
      map.set(getHash(key), node);
      entries++;
    }
    if(((1.0*entries) / buckets) >= LOAD_THRESHOLD){
      ArrayList<HashNode> tempArrayList = map;

      buckets *= 2;

      map = new ArrayList<HashNode>(buckets);

      entries = 0;

      for(int i = 0; i < buckets; i++) {
        map.add(null);
      }

      for(HashNode node : tempArrayList) {
        while(node != null) {
          put(node.key, node.value);
          node = node.next;
        }
      }
    }
  }

  boolean containsKey(String key){
    return this.get(key) != null;
  }

  String remove(String key){
    HashNode head = map.get(getHash(key));
    if(head != null){
      if(head.key == key){
        map.set(getHash(key), head.next);
        return head.value;
      } else {
        HashNode prev = head;
        HashNode current = null;
        while(prev.next != null){
          current = prev.next;
          if(current.key == key){
            prev.next = current.next;
            return current.value;
          }
        }
      }
    }
    return null;
  }
}