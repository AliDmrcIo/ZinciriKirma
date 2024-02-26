package com.alidemirci.dontbreakthechain;

import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.List;
 public class RangeDecorator implements DayViewDecorator {
     public int color;
     private CalendarDay startDay;
     private CalendarDay endDay;

     public RangeDecorator(int color, CalendarDay startDay, CalendarDay endDay) {
         this.color = color;
         this.startDay = startDay;
         this.endDay = endDay;
     }

     @Override
     public boolean shouldDecorate(CalendarDay day) {
         return day.isAfter(startDay) && day.isBefore(endDay) || day.equals(startDay) || day.equals(endDay);
     }

     @Override
     public void decorate(DayViewFacade view) {
         view.addSpan(new ForegroundColorSpan(color));
     }
}
