import React from 'react'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import Navbar from './components/Navbar'
import Home from './components/Home'
import RecipeList from './components/RecipeList'
import RecipeDetail from './components/RecipeDetail'
import AddRecipeForm from './components/AddRecipeForm'
import EditRecipeForm from './components/EditRecipeForm'

function App() {
  return (
    <Router>
      <div className="App">
        <Navbar />
        <div className="container mt-4">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/recipes" element={<RecipeList />} />
            <Route path="/recipe/:id" element={<RecipeDetail />} />
            <Route path="/recipes-add-form" element={<AddRecipeForm />} />
            <Route path="/recipe/edit/:id" element={<EditRecipeForm />} />
          </Routes>
        </div>
      </div>
    </Router>
  )
}

export default App