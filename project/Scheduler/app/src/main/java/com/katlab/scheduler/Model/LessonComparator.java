package com.katlab.scheduler.Model;

import java.util.Comparator;

public class LessonComparator implements Comparator<Lesson>{
    @Override
    public int compare(Lesson o1, Lesson o2) {
        return o1.compareTo(o2);
    }
}
