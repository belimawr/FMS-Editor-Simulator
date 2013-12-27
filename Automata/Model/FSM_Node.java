package Automata.Model;

import Automata.Figures.CountingFigure;

/**
 * Author: Tiago de França Queiroz
 * Date: 21/12/13
 *
 * Copyright Tiago de França Queiroz, 2013.
 *
 * This file is part of Automata.
 *
 * Automata is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Automata is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Automata. If not, see <http://www.gnu.org/licenses/>.
 */
public class FSM_Node
{
	FSM_Node zero, one;
	CountingFigure myFigure;

	public FSM_Node()
	{
		zero = null;
		one = null;
		myFigure = null;
	}

	public FSM_Node(CountingFigure figure)
	{
		zero = null;
		one = null;
		myFigure = figure;
	}

	boolean isValid()
	{
		if(zero != null && one != null)
			return true;
		return false;
	}

	public FSM_Node getZero()
	{
		return zero;
	}

	public void setZero(FSM_Node zero)
	{
		this.zero = zero;
	}

	public FSM_Node getOne()
	{
		return one;
	}

	public void setOne(FSM_Node one)
	{
		this.one = one;
	}

	public String toString()
	{
		if(zero != null && one != null)
			return String.format("\"%s\": 0:{%s}, 1:{%s}", myFigure, zero.myFigure, one.myFigure);
		else if(zero == null && one != null)
			return String.format("\"%s\": 0:{%s}, 1:{%s}", myFigure, zero, one.myFigure);
		else if(zero != null && one == null)
			return String.format("\"%s\": 0:{%s}, 1:{%s}", myFigure, zero.myFigure, one);
		else
			return String.format("\"%s\": 0:{%s}, 1:{%s}", myFigure, zero, one);
	}
}
