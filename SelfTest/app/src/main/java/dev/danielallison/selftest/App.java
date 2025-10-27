package dev.danielallison.selftest;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {}
}

class Domain {
    interface Answer<T> {
        public abstract T getValue();

        public abstract Boolean guess(T input);
    }

    static final class MultipleChoiceAnswer implements Answer<Integer> {
        private final ArrayList<String> options;
        private final Integer correctAnswerIndex;

        public MultipleChoiceAnswer(ArrayList<String> options, Integer correctAnswerIndex) {
            this.options = options;
            this.correctAnswerIndex = correctAnswerIndex;
        }

        @Override
        public Integer getValue() {
            return this.correctAnswerIndex;
        }

        @Override
        public Boolean guess(Integer input) {
            if (input == this.correctAnswerIndex) {
                return true;
            } else {
                return false;
            }
        }
    }

    static final class YesNoAnswer implements Answer<Boolean> {
        private final Boolean value;

        public YesNoAnswer(Boolean value) {
            this.value = value;
        }

        @Override
        public Boolean getValue() {
            return this.value;
        }

        @Override
        public Boolean guess(Boolean input) {
            if (input == this.value) {
                return true;
            } else {
                return false;
            }
        }
    }

    static final class NumberAnswer implements Answer<Double> {
        private final Double value;

        public NumberAnswer(Double value) {
            this.value = value;
        }

        @Override
        public Double getValue() {
            return this.value;
        }

        @Override
        public Boolean guess(Double input) {
            if (input == this.value) {
                return true;
            } else {
                return false;
            }
        }
    }

    static class QAPair {
        private String question;
        private Answer answer;
    }

    interface TestBankRepository {
        ArrayList<QAPair> parsePairs(ArrayList<String> raw);
    }
}
