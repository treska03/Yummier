import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import recipeService from '../service/recipeService';


interface Recipe {
  title: string;
  description: string;
  timeNeeded: number;
  difficulty: string;
  category: string;
  ingredients: string[];
  instructions: string[];
}

function EditRecipe() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [recipe, setRecipe] = useState<Recipe>({
    title: '',
    description: '',
    timeNeeded: 0,
    difficulty: 'EASY',
    category: 'BREAKFAST',
    ingredients: [''],
    instructions: ['']
  });

  const [originalRecipe, setOriginalRecipe] = useState<Recipe | null>(null);

  useEffect(() => {
    async function fetchRecipe() {
      try {
        const fetchedRecipe: Recipe = await recipeService.getById(id);
        setRecipe({
          title: fetchedRecipe.title,
          description: fetchedRecipe.description,
          timeNeeded: fetchedRecipe.timeNeeded,
          difficulty: fetchedRecipe.difficulty,
          category: fetchedRecipe.category,
          ingredients: fetchedRecipe.ingredients.length ? fetchedRecipe.ingredients : [''],
          instructions: fetchedRecipe.instructions.length ? fetchedRecipe.instructions : ['']
        });
        setOriginalRecipe(fetchedRecipe);
      } catch (error) {
        console.error('Error fetching recipe:', error);
      }
    }
    fetchRecipe();
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setRecipe(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleArrayChange = (index: number, field: 'ingredients' | 'instructions', value: string) => {
    setRecipe(prev => ({
      ...prev,
      [field]: prev[field].map((item, i) => (i === index ? value : item))
    }));
  };

  const addArrayField = (field: 'ingredients' | 'instructions') => {
    setRecipe(prev => ({
      ...prev,
      [field]: [...prev[field], '']
    }));
  };

  const removeArrayField = (field: 'ingredients' | 'instructions', index: number) => {
    setRecipe(prev => ({
      ...prev,
      [field]: prev[field].filter((_, i) => i !== index)
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const updatedRecipe: Recipe = {
        ...recipe,
      };

      await recipeService.editRecipe(id, updatedRecipe);
      navigate('/recipes');
    } catch (error) {
      console.error('Error updating recipe:', error);
    }
  };

  return (
    <div className="recipe-form p-4">
      <h2 className="text-center mb-4">Edit Recipe</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label className="form-label">Recipe Name</label>
          <input
            type="text"
            className="form-control"
            name="title"
            value={recipe.title}
            onChange={handleChange}
            placeholder={originalRecipe?.title || ''}
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Description</label>
          <textarea
            className="form-control"
            name="description"
            value={recipe.description}
            onChange={handleChange}
            placeholder={originalRecipe?.description || ''}
            required
          />
        </div>

        <div className="row mb-3">
          <div className="col">
            <label className="form-label">Cooking Time</label>
            <input
              type="number"
              className="form-control"
              name="timeNeeded"
              min="1"
              max="213769"
              value={recipe.timeNeeded}
              onChange={handleChange}
              placeholder={originalRecipe?.timeNeeded.toString() || ''}
              required
            />
          </div>
          <div className="col">
            <label className="form-label">Difficulty</label>
            <select
              className="form-control"
              name="difficulty"
              value={recipe.difficulty}
              onChange={handleChange}
            >
              <option value="EASY">Easy</option>
              <option value="MEDIUM">Medium</option>
              <option value="HARD">Hard</option>
            </select>
          </div>
          <div className="col">
            <label className="form-label">Category</label>
            <select
              className="form-control"
              name="category"
              value={recipe.category}
              onChange={handleChange}
            >
              <option value="BREAKFAST">Breakfast</option>
              <option value="LUNCH">Lunch</option>
              <option value="SNACK">Snack</option>
              <option value="DINNER">Dinner</option>
            </select>
          </div>
        </div>

        <div className="mb-3">
          <label className="form-label">Ingredients</label>
          {recipe.ingredients.map((ingredient, index) => (
            <div key={index} className="input-group mb-2">
              <input
                type="text"
                className="form-control"
                value={ingredient}
                onChange={(e) => handleArrayChange(index, 'ingredients', e.target.value)}
                placeholder={originalRecipe?.ingredients[index] || ''}
                required
              />
              <button
                type="button"
                className="btn btn-outline-danger"
                onClick={() => removeArrayField('ingredients', index)}
              >
                ✕
              </button>
            </div>
          ))}
          <button
            type="button"
            className="btn btn-outline-primary btn-sm"
            onClick={() => addArrayField('ingredients')}
          >
            + Add Ingredient
          </button>
        </div>

        <div className="mb-3">
          <label className="form-label">Instructions</label>
          {recipe.instructions.map((instruction, index) => (
            <div key={index} className="input-group mb-2">
              <button type="button" className="btn btn-outline-dark instruction-number">
                {index + 1}.
              </button>
              <input
                type="text"
                className="form-control"
                value={instruction}
                onChange={(e) => handleArrayChange(index, 'instructions', e.target.value)}
                placeholder={originalRecipe?.instructions[index] || ''}
                required
              />
              <button
                type="button"
                className="btn btn-outline-danger"
                onClick={() => removeArrayField('instructions', index)}
              >
                ✕
              </button>
            </div>
          ))}
          <button
            type="button"
            className="btn btn-outline-primary btn-sm"
            onClick={() => addArrayField('instructions')}
          >
            + Add Instruction
          </button>
        </div>

        <div className="text-center">
          <button type="submit" className="btn btn-primary">
            Edit Recipe
          </button>
        </div>
      </form>
    </div>
  );
}

export default EditRecipe;
