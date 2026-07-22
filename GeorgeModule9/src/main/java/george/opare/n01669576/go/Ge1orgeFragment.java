// George Opare n01669576
package george.opare.n01669576.go;

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
import java.util.ArrayList;

public class Ge1orgeFragment extends Fragment {

    private ArrayList<Course> courseList = new ArrayList<>();
    private CourseAdapter adapter;
    private EditText nameInput;
    private EditText descInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ge1orge, container, false);

        nameInput = view.findViewById(R.id.geoCourseName);
        descInput = view.findViewById(R.id.geoCourseDesc);
        Button addButton = view.findViewById(R.id.geoAddButton);
        Button saveButton = view.findViewById(R.id.geoSaveButton);
        Button deleteButton = view.findViewById(R.id.geoDeleteButton);

        RecyclerView recyclerView = view.findViewById(R.id.geoRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new CourseAdapter(courseList);
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            String desc = descInput.getText().toString().trim();

            if (name.isEmpty() || desc.isEmpty()) {
                Toast.makeText(requireContext(),
                        "George Opare - Enter course name and description",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            courseList.add(new Course(name, desc));
            adapter.notifyItemInserted(courseList.size() - 1);

            nameInput.setText("");
            descInput.setText("");
        });

        saveButton.setOnClickListener(v -> {
            // Gson save to SharedPreferences — next commit
        });

        deleteButton.setOnClickListener(v -> {
            // delete — later commit
        });

        return view;
    }
}