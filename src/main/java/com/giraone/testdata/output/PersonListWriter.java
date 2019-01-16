package com.giraone.testdata.output;

import com.giraone.testdata.Person;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public interface PersonListWriter {
    void write(List<Person> personList, PrintStream out) throws IOException;
}
