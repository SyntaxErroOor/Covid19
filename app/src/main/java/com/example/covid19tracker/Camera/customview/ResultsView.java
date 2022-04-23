
package com.example.covid19tracker.Camera.customview;

import com.example.covid19tracker.Camera.tflite.Classifier.Recognition;

import java.util.List;

public interface ResultsView {
  public void setResults(final List<Recognition> results);
}
