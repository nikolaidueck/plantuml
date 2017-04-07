package net.sourceforge.plantuml.DSL;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

import dmd.dsl.DSL;
import dmd.dsl.DSLException;
import generator.plantuml.PlantUmlGenerator;
import net.sourceforge.plantuml.text.AbstractDiagramTextProvider;

public class DMDStructureDslTextProvider extends AbstractDiagramTextProvider {

	@Inject Logger logger;
	
	public DMDStructureDslTextProvider() {
		setEditorType(ITextEditor.class);
	}

	public boolean supportsSelection(ISelection selection) {
		return false;
	}
	
	@Override
	protected String getDiagramText(IEditorPart editorPart, IEditorInput editorInput, ISelection ignore) {
		if (! (editorInput instanceof IFileEditorInput && "groovy".equals(((IFileEditorInput) editorInput).getFile().getFileExtension()))) {
			return null;
		}
		IFile file = ((IFileEditorInput) editorInput).getFile();
		String result = "";
		DSL dsl = new DSL();
		try {
			result = dsl.generatePlantUml(file.getRawLocation().makeAbsolute().toFile());
		} catch (DSLException e) {
//			logger.log(Level.SEVERE, "Fehler beim generieren von PlantUml", e);
			return "";
		}
		return (result.length() > 0 ? result : null);
	}
}
