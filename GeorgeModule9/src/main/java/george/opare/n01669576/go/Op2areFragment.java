// George Opare n01669576
package george.opare.n01669576.go;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.snackbar.Snackbar;
import java.io.File;
import java.io.IOException;

public class Op2areFragment extends Fragment {

    private EditText fileNameInput;
    private EditText fileContentsInput;
    private ToggleButton persistentToggle;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_op2are, container, false);
        rootView = view;

        fileNameInput = view.findViewById(R.id.geoFileName);
        fileContentsInput = view.findViewById(R.id.geoFileContents);
        persistentToggle = view.findViewById(R.id.geoPersistentToggle);

        Button createButton = view.findViewById(R.id.geoCreateFile);
        Button deleteButton = view.findViewById(R.id.geoDeleteFile);
        Button writeButton = view.findViewById(R.id.geoWriteFile);
        Button readButton = view.findViewById(R.id.geoReadFile);

        createButton.setOnClickListener(v -> {
            if (isFileNameMissing()) return;
            createFile();
        });

        deleteButton.setOnClickListener(v -> {
            if (isFileNameMissing()) return;
            deleteFile();
        });

        writeButton.setOnClickListener(v -> {
            if (isFileNameMissing()) return;
            // write — next commit
        });

        readButton.setOnClickListener(v -> {
            if (isFileNameMissing()) return;
            // read — next commit
        });

        return view;
    }

    // persistent = internal files dir, otherwise cache dir
    private File getTargetDirectory() {
        return persistentToggle.isChecked()
                ? requireContext().getFilesDir()
                : requireContext().getCacheDir();
    }

    private String getFileName() {
        return fileNameInput.getText().toString().trim();
    }

    // one shared check so the snackbar code is not repeated
    private boolean isFileNameMissing() {
        if (getFileName().isEmpty()) {
            Snackbar.make(rootView, R.string.geo_name_missing, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.geo_dismiss, v -> { })
                    .show();
            return true;
        }
        return false;
    }

    private void createFile() {
        File file = new File(getTargetDirectory(), getFileName());
        try {
            if (file.createNewFile()) {
                Toast.makeText(requireContext(),
                        "Created: " + file.getName(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(),
                        "File already exists", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(requireContext(),
                    "Error creating file", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteFile() {
        File file = new File(getTargetDirectory(), getFileName());
        if (file.exists() && file.delete()) {
            Toast.makeText(requireContext(),
                    "Deleted: " + file.getName(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(),
                    "File not found", Toast.LENGTH_SHORT).show();
        }
    }
}