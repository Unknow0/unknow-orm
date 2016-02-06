package unknow.orm.annotation;

import java.util.*;

import javax.annotation.processing.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import javax.tools.Diagnostic.Kind;

@SupportedAnnotationTypes("unknow.orm.annotation.Validate")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class OrmProcessor extends AbstractProcessor
	{
	public OrmProcessor()
		{
		}

	public synchronized void init(ProcessingEnvironment processingEnv)
		{
		super.init(processingEnv);
		}

	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
		{
		processingEnv.getMessager().printMessage(Kind.NOTE, "process");
//		for(TypeElement t:annotations)
//			{
//			processingEnv.getMessager().printMessage(Kind.NOTE, "test: "+t);
		Set<? extends Element> elements=roundEnv.getRootElements();//roundEnv.getElementsAnnotatedWith(Validate.class);
		for(Element e:elements)
			{
			e.accept(new ElementVisitor<Object,Object>()
				{
					public Object visitPackage(PackageElement e, Object p)
						{
						processingEnv.getMessager().printMessage(Kind.NOTE, "package: "+e);
						for(Element elem:e.getEnclosedElements())
							elem.accept(this, p);
						return null;
						}

					public Object visitType(TypeElement e, Object p)
						{
						processingEnv.getMessager().printMessage(Kind.NOTE, "type: "+e);
						for(Element elem:e.getEnclosedElements())
							elem.accept(this, p);
						return null;
						}

					public Object visitVariable(VariableElement e, Object p)
						{
						processingEnv.getMessager().printMessage(Kind.NOTE, "var: "+e);
						return null;
						}

					public Object visitExecutable(ExecutableElement e, Object p)
						{
						processingEnv.getMessager().printMessage(Kind.NOTE, "method: "+e);
						for(Element elem:e.getEnclosedElements())
							elem.accept(this, p);
						return null;
						}

					public Object visitTypeParameter(TypeParameterElement e, Object p)
						{
						processingEnv.getMessager().printMessage(Kind.NOTE, "typeparam: "+e);
						return null;
						}

					public Object visit(Element e, Object p)
						{
						processingEnv.getMessager().printMessage(Kind.NOTE, "visite: "+e);
						return null;
						}

					public Object visit(Element e)
						{
						processingEnv.getMessager().printMessage(Kind.NOTE, "visite: "+e);
						return null;
						}

					public Object visitUnknown(Element e, Object p)
						{
						processingEnv.getMessager().printMessage(Kind.NOTE, "unknown: "+e);
						return null;
						}
				}, null);
			}
//			}
		return true;
		}

	public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation, ExecutableElement member, String userText)
		{
		processingEnv.getMessager().printMessage(Kind.NOTE, "getCompletion");

		return Collections.emptyList();
		}
	}
