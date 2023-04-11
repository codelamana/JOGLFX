package com.jakob.joglfx;

import com.jakob.joglfx.geometry.GeometryObject;
import com.jakob.joglfx.geometry.primitives.Cube;
import com.jakob.joglfx.model.scene.SceneModel;
import com.jakob.joglfx.object.OBJloader;
import org.joml.Quaternionf;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class ProjectLoader {

    /**
     *
     * This class functions as manager for multiple projects and also manages selection, loading and saving of a project
     *
     */

    static String workspaceDir = "";

    public ProjectLoader() {

    }

    /**
     * Load a test project for testing purposes, contains some basic shapes
     * @return
     */
    public static Project getTestProject(){

        Project temp = new Project("Test Project");

        GeometryObject rootGeometryObject = loadGeometryObjectRoot();

        SceneModel sceneModel = new SceneModel(rootGeometryObject);
        sceneModel.setRootGeometryObject(rootGeometryObject);

        temp.setRootGeometryObject(rootGeometryObject);
        temp.setSceneModel(sceneModel);

        return temp;

    }

    /**
     *
     * Save a project by collecting all important items and serialize them in a JSON String
     *
     * @param project project to be saved
     */
    public static void saveProject(Project project){

        JsonObjectBuilder currentObject = Json.createObjectBuilder().add("projectName", project.getProjectName());


        JsonObjectBuilder camera = Json.createObjectBuilder()
                .add("cameraX", project.getSceneModel().getCamera().getEye().x())
                .add("cameraY", project.getSceneModel().getCamera().getEye().y())
                .add("cameraZ", project.getSceneModel().getCamera().getEye().z())

                .add("centerX", project.getSceneModel().getCamera().getCenter().x())
                .add("centerY", project.getSceneModel().getCamera().getCenter().y())
                .add("centerZ", project.getSceneModel().getCamera().getCenter().z())

                .add("upX", project.getSceneModel().getCamera().getUp().x())
                .add("upY", project.getSceneModel().getCamera().getUp().y())
                .add("upZ", project.getSceneModel().getCamera().getUp().z());

        JsonObjectBuilder sceneModel = Json.createObjectBuilder()
                .add("camera", camera)
                .add("animated", true);

        currentObject.add("sceneModel" , sceneModel);

        currentObject.add("children", buildGeometryObjectJSONObject(project.getRootGeometryObject()));

        System.out.println(currentObject.build().toString());
    }

    /**
     * Helper method for creating the JSON Object for a GeometryObject and all of its children
     *
     * @param root GeometryObject to be serialized
     * @return JSONObject instance
     */
    private static JsonObject buildGeometryObjectJSONObject(GeometryObject root){
        JsonObjectBuilder currentObject = Json.createObjectBuilder();

        // add all information
        currentObject.add("name", root.getName());
        currentObject.add(
                "worldPosition" , Json.createObjectBuilder()
                        .add("worldX", root.worldSpacePosition.x())
                        .add("worldY", root.worldSpacePosition.y())
                        .add("worldZ", root.worldSpacePosition.z())
                        .build()
        );
        currentObject.add(
                "rotation" , Json.createObjectBuilder()
                        .add("rotationX", root.rotation.x())
                        .add("rotationY", root.rotation.y())
                        .add("rotationZ", root.rotation.z())
                        .build()
        );

        // create JSON array for children and add all children by recursion
        JsonArrayBuilder children = Json.createArrayBuilder();
        if(root.getChildren().size() != 0){
            for(GeometryObject next : root.getChildren()) {
                children.add(buildGeometryObjectJSONObject(next));
            }
        }
        currentObject.add("children", children.build());

        return currentObject.build();
    }


    /**
     * Helper Method for building objects in test project
     *
     * @return an example tree of cubes
     */
    private static GeometryObject loadGeometryObjectRoot() {

        GeometryObject root1 = new GeometryObject("Würfel");
        GeometryObject c1 = new GeometryObject("Container");
        c1.addChild(new Cube("Another Cube", 2,2,2, 0.25, 0.4, 1), new Cube("W1", 0,0,0, 1,1,1));
        root1.addChild(c1);

        Cube rotTest = new Cube( "W2", 1,1,1, 0.5,0.5,0.5);
        rotTest.setRotation(new Quaternionf().rotateLocalX((float)(Math.PI/4)));
        root1.addChild(rotTest);

        GeometryObject root2 = new GeometryObject("Andere Würfel");
        root2.addChild(new Cube("dritter Würfel", -1,1,1, 0.25, 0.25, 0.25));
        root2.addChild(new Cube( "W4", 1,-1,1, 0.5,0.5,0.5));
        OBJloader obJloader = new OBJloader("teapot.obj");
        //root2.addChild(obJloader.load());

        GeometryObject temp = new GeometryObject("Projekt Root");
        temp.addChild(root1);
        temp.addChild(root2);

        return temp;

    }

}
