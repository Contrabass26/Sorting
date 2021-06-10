import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class DataManager {

    static int set_length = 100;
    static int step_delay = 1;

    List<Integer> data_set;

    public DataManager() {
        data_set = new ArrayList<>();
        for (int i = 1; i <= set_length; i++) {
            data_set.add(i);
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(data_set);
    }

    public void swap(int first_index) {
        int temp = data_set.get(first_index);
        data_set.set(first_index, data_set.get(first_index + 1));
        data_set.set(first_index + 1, temp);
    }

    public int get(int index) {
        return data_set.get(index);
    }

    public void bubble_sort() {
        Thread thread = new Thread(() -> {
            Window.canvas.repaint();
            int length = data_set.size();
            boolean swapped = true;
            while (length > 0 && swapped) {
                swapped = false;
                length--;
                for (int i = 0; i < length; i++) {
                    if (get(i) > get(i + 1)) {
                        swap(i);
                        swapped = true;
                        Window.canvas.repaint();
                        try {
                            sleep(step_delay);
                        } catch (InterruptedException e) {
                            // Do nothing
                        }
                    }
                }
            }
            Window.canvas.repaint();
        });
        thread.start();
    }

    public void insertion_sort() {
        Thread thread = new Thread(() -> {
            Window.canvas.repaint();
            for (int i = 0; i < data_set.size(); i++) {
                int current = get(i);
                int p = i;
                while (p > 0 && get(p - 1) > current) {
                    data_set.set(p, get(p - 1));
                    p--;
                    Window.canvas.repaint();
                    try {
                        sleep(step_delay);
                    } catch (InterruptedException e) {
                        // Do nothing
                    }
                }
                data_set.set(p, current);
            }
            Window.canvas.repaint();
        });
        thread.start();
    }

    public void merge_sort() {
        Thread thread = new Thread(() -> {
            Window.canvas.repaint();
            while (!data_set.stream().sorted().collect(Collectors.toList()).equals(data_set)) {
                int index = 0;
                while (index < data_set.size() - 1) {
                    data_set = merge(data_set.subList(0, index + 1), data_set.subList(index + 1, data_set.size()));
                    index ++;
                    Window.canvas.repaint();
                    try {
                        sleep(step_delay);
                    } catch (InterruptedException e) {
                        // Do nothing
                    }
                }
            }
            Window.canvas.repaint();
        });
        thread.start();
    }

    public List<Integer> merge(List<Integer> list1, List<Integer> list2) {
        List<Integer> new_list = new ArrayList<>();
        int index1 = 0, index2 = 0;
        while (index1 < list1.size() && index2 < list2.size()) {
            if (list1.get(index1) > list2.get(index2)) {
                new_list.add(list2.get(index2));
                index2 ++;
            } else if (list1.get(index1) < list2.get(index2)) {
                new_list.add(list1.get(index1));
                index1 ++;
            } else if (list1.get(index1).equals(list2.get(index2))) {
                new_list.add(list1.get(index1));
                new_list.add(list2.get(index2));
                index1 ++;
                index2 ++;
            }
        }
        if (index1 < list1.size()) {
            for (int i = index1; i < list1.size(); i++) {
                new_list.add(list1.get(i));
            }
        } else if (index2 < list2.size()) {
            for (int i = index2; i < list2.size(); i++) {
                new_list.add(list2.get(i));
            }
        }
        return new_list;
    }
}
