package Automata.Decorators;

import Automata.Exceptions.Automata_Exception;
import Automata.Figures.CountingFigure;
import CH.ifa.draw.figure.DecoratorFigure;
import CH.ifa.draw.framework.Figure;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Tiago de França Queiroz
 * Date: 02/01/14
 *
 * Copyright Tiago de França Queiroz, 2014.
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
public abstract class AutomataDecorator extends DecoratorFigure
{
	protected CountingFigure parent;

	protected AutomataDecorator(Figure figure)
	{
		super(figure);
		if(figure instanceof AutomataDecorator)
			parent = ((AutomataDecorator) figure).getParent();
	}

	private List<AutomataDecorator> getDecorators()
	{
		List<AutomataDecorator> l = new ArrayList<AutomataDecorator>();
		Figure decorated;
		Figure current = this;

		l.add(this);
		while(current instanceof AutomataDecorator)
		{
			decorated = ((AutomataDecorator) current).fComponent;
			if(decorated instanceof AutomataDecorator)
				l.add((AutomataDecorator) decorated);
			current = decorated;
		}
		if(l.size() > 2)
			throw new Automata_Exception("Too many decorators!");
		return l;
	}

	public FinalStateDecorator getEndDecorator()
	{
		List<AutomataDecorator> decs = getDecorators();
		for(AutomataDecorator d: decs)
		{
			if(d instanceof FinalStateDecorator)
				return (FinalStateDecorator) d;
		}
		return null;
	}

	public StartStateDecorator getStartDecorator()
	{
		List<AutomataDecorator> decs = getDecorators();
		for(AutomataDecorator d: decs)
			if(d instanceof StartStateDecorator)
				return (StartStateDecorator) d;
		return null;
	}

	@Override
	public void decorate(Figure figure)
	{
		if(figure instanceof AutomataDecorator || figure instanceof CountingFigure)
		{
			if(figure instanceof AutomataDecorator)
			{
				super.decorate(figure);
			}
			else
			{
				parent = (CountingFigure) figure;
				super.decorate(figure);
			}
		}
		else
		{
			throw new Automata_Exception(String.format(
					"%s cannot be decorated by AutomataDecorator\n",
					figure.getClass().toString()));
		}
	}

	public CountingFigure getParent()
	{
		return parent;
	}

	public Figure getFigure()
	{
		return fComponent;
	}
}
