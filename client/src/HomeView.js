import React from 'react'
import { Link } from 'react-router-dom'
const HomeView = () => {
  return (
    <div>
      <Link to='/login'>
        <button>login</button>
      </Link>
    </div>
  )
}

export default HomeView
