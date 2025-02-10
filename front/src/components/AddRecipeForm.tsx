import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import recipeService from '../service/recipeService'


function AddRecipe() {
  const navigate = useNavigate()
  const [recipe, setRecipe] = useState({
    title: '',
    description: '',
    timeNeeded: '',
    difficulty: 'EASY',
    category: 'BREAKFAST',
    ingredients: [''],
    instructions: ['']
  })

  const handleChange = (e) => {
    const { name, value } = e.target
    setRecipe(prev => ({
      ...prev,
      [name]: value
    }))
  }

  const handleArrayChange = (index, field, value) => {
    setRecipe(prev => ({
      ...prev,
      [field]: prev[field].map((item, i) => i === index ? value : item)
    }))
  }

  const addArrayField = (field) => {
    setRecipe(prev => ({
      ...prev,
      [field]: [...prev[field], '']
    }))
  }

  const removeArrayField = (field, index) => {
    setRecipe(prev => ({
      ...prev,
      [field]: prev[field].filter((_, i) => i !== index)
    }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    try {
        recipe.difficulty = recipe.difficulty.toUpperCase();
        recipe.category = recipe.category.toUpperCase();
        
        const addedRecipe = await recipeService.addRecipe(recipe);
        console.log('Recipe added:', addedRecipe);
        navigate('/recipes')
        console.log('Recipe added', recipe )
    } catch (error) {
        console.error('Error adding recipe:', error);
    }
    
    
  }

  return (
    <div className="recipe-form p-4">
      <h2 className="text-center mb-4">Add New Recipe</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label className="form-label">Recipe Name</label>
          <input
            type="text"
            className="form-control"
            name="title"
            value={recipe.title}
            onChange={handleChange}
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
              <option>Easy</option>
              <option>Medium</option>
              <option>Hard</option>
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
              <option>Breakfast</option>
              <option>Lunch</option>
              <option>Snack</option>
              <option>Dinner</option>
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
              <input
                type="text"
                className="form-control"
                value={instruction}
                onChange={(e) => handleArrayChange(index, 'instructions', e.target.value)}
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
            Add Recipe
          </button>
        </div>
      </form>
    </div>
  )
}

export default AddRecipe