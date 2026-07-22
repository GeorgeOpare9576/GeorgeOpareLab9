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
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
            writeFile();
        });

        readButton.setOnClickListener(v -> {
            if (isFileNameMissing()) return;
            readFile();
        });

        return view;
    }

    private File getTargetDirectory() {
        return persistentToggle.isChecked()
                ? requireContext().getFilesDir()
                : requireContext().getCacheDir();
    }

    private String getFileName() {
        return fileNameInput.getText().toString().trim();
    }

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
                Toast.makeText(requireContext(), R.string.geo_file_created,
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), R.string.geo_file_exists,
                        Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(requireContext(), R.string.geo_file_error,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteFile() {
        File file = new File(getTargetDirectory(), getFileName());
        if (file.exists() && file.delete()) {
            Toast.makeText(requireContext(), R.string.geo_file_deleted,
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), R.string.geo_file_not_found,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void writeFile() {
        String content = fileContentsInput.getText().toString().trim();

        if (content.isEmpty()) {
            Toast.makeText(requireContext(), R.string.geo_content_missing,
                    Toast.LENGTH_LONG).show();
            return;
        }

        File file = new File(getTargetDirectory(), getFileName());
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content.getBytes());
            fileContentsInput.setText("");          // clear the field after writing
            Toast.makeText(requireContext(), R.string.geo_file_written,
                    Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(requireContext(), R.string.geo_file_error,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void readFile() {
        File file = new File(getTargetDirectory(), getFileName());

        if (!file.exists()) {
            Toast.makeText(requireContext(), R.string.geo_file_not_found,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[(int) file.length()];
            int read = fis.read(buffer);
            if (read > 0) {
                fileContentsInput.setText(new String(buffer));
            }
        } catch (IOException e) {
            Toast.makeText(requireContext(), R.string.geo_file_error,
                    Toast.LENGTH_SHORT).show();
        }
    }
}