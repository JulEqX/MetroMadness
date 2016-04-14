package com.unimelb.swen30006.metromadness;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;

public class MetroMadness extends ApplicationAdapter {

	// The width of the world in unitless dimensions
    private static final int WORLD_WIDTH = 1200;
    private static final int WORLD_HEIGHT = 1200;

    // Viewport state
    private static final int VIEWPORT_WIDTH = 200;
    
    //Font size
    private static final int FONTSIZE = 12;
    private static final int HEADLINESIZE = 40;
    
    private static final String FILEPATH = "assets/maps/melbourne1.xml";

	// Data for simulation, rendering and camera.
	private Simulation sim;
	private ShapeRenderer shapeRenderer;
	private OrthographicCamera camera;
	
	// Font
	private BitmapFont smaller;
	private BitmapFont header;

	@Override
	public void resize(int width, int height) {
        camera.viewportWidth = VIEWPORT_WIDTH;
        camera.viewportHeight = VIEWPORT_WIDTH * (float)height/width;
        camera.update();
	}

	@Override
	public void create () {
		// Create the simulation
		sim = new Simulation(FILEPATH);		
		
		// Setup our 2D Camera
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_WIDTH * (h / w));
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();
		
		// Create our shape renderer
		shapeRenderer = new ShapeRenderer();
		
		// Create our font
		FreeTypeFontGenerator generator = 
				new FreeTypeFontGenerator(Gdx.files.internal("fonts/Gotham-Book.ttf"));
		FreeTypeFontParameter parameter = 
				new FreeTypeFontParameter();
		parameter.size = FONTSIZE;
		smaller = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose(); // don't forget to dispose to avoid memory leaks!

		FreeTypeFontGenerator headlineGen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Gotham-Bold.ttf"));
		FreeTypeFontParameter headlineParam = new FreeTypeFontParameter();
		headlineParam.size = HEADLINESIZE;
		header = headlineGen.generateFont(headlineParam); // font size 12 pixels
		headlineGen.dispose(); // don't forget to dispose to avoid memory leaks!

		
		// Setup fonts
		 smaller.setColor(Color.GRAY);
		 header.setColor(Color.BLACK);

	}

	@Override
	public void render () {
		// Clear the graphics to white
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Handle user input
		handleInput();
		
		// Update the simulation and camera
		camera.update();
		sim.update();
		
		// Render the simulation
		 shapeRenderer.setProjectionMatrix(camera.combined);
		 
		 // Render all filled shapes.
		 shapeRenderer.begin(ShapeType.Filled);
		 sim.render(shapeRenderer);
		 shapeRenderer.end();
		 
		 // Begin preparations to render text
		 SpriteBatch batch = new SpriteBatch();
		 batch.begin();
		 
		 // Render Header
		 header.getData().setScale(0.5f);
		 header.draw(batch, "metro madness.", 10, Gdx.graphics.getHeight()-10);
		 batch.end();
		 
	}
	
	/**
	 * NEED TO DO DOCUMENTATION
	 * 
	 * */
    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.zoom += 0.1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
        	camera.zoom -= 0.1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
        	camera.translate(-3f, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
        	camera.translate(3f, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
        	camera.translate(0, -3f, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
        	camera.translate(0, 3f, 0);
        }

        
        camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, WORLD_WIDTH/camera.viewportWidth);
        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, WORLD_WIDTH - effectiveViewportWidth / 2f);
        camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, WORLD_HEIGHT - effectiveViewportHeight / 2f);
    }

}
