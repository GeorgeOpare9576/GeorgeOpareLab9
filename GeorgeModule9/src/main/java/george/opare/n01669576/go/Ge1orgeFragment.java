// George Opare n01669576
package george.opare.n01669576.go;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Ge1orgeFragment extends Fragment {

    private static final String GEO_KEY_COURSES = "geoCourseList";

    private ArrayList<Course> courseList = new ArrayList<>();
    private CourseAdapter adapter;
    private EditText nameInput;
    private EditText descInput;
    private SharedPreferences prefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ge1orge, container, false);

        prefs = requireContext().getSharedPreferences(
                OpareActivity9.GEO_PREFS, android.content.Context.MODE_PRIVATE);

        nameInput = view.findViewById(R.id.geoCourseName);
        descInput = view.findViewById(R.id.geoCourseDesc);
        Button addButton = view.findViewById(R.id.geoAddButton);
        Button saveButton = view.findViewById(R.id.geoSaveButton);
        Button deleteButton = view.findViewById(R.id.geoDeleteButton);

        RecyclerView recyclerView = view.findViewById(R.id.geoRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new CourseAdapter(courseList);
        recyclerView.setAdapter(adapter);

        loadCourses();

        addButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            String desc = descInput.getText().toString().trim();

            if (name.isEmpty() || desc.isEmpty()) {
                Toast.makeText(requireContext(), R.string.geo_enter_course,
                        Toast.LENGTH_SHORT).show();
                return;
            }

            courseList.add(new Course(name, desc));
            adapter.notifyItemInserted(courseList.size() - 1);

            nameInput.setText("");
            descInput.setText("");
        });

        saveButton.setOnClickListener(v -> {
            saveCourses();
            Toast.makeText(requireContext(), R.string.geo_saved,
                    Toast.LENGTH_SHORT).show();
        });

        deleteButton.setOnClickListener(v -> {
            // delete — next commit
        });

        return view;
    }

    // convert the ArrayList to a JSON string and store it
    private void saveCourses() {
        Gson gson = new Gson();
        String json = gson.toJson(courseList);
        prefs.edit().putString(GEO_KEY_COURSES, json).apply();
    }

    // read the JSON string back into the ArrayList
    private void loadCourses() {
        String json = prefs.getString(GEO_KEY_COURSES, null);
        if (json == null) {
            return;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Course>>() {}.getType();
        ArrayList<Course> saved = gson.fromJson(json, type);
        if (saved != null) {
            courseList.clear();
            courseList.addAll(saved);
            adapter.notifyDataSetChanged();
        }
    }
}