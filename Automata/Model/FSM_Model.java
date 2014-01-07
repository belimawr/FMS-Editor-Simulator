package Automata.Model;

import Automata.Decorators.AutomataDecorator;
import Automata.Decorators.CurrentStateDecorator;
import Automata.Decorators.StartStateDecorator;
import Automata.Figures.CountingFigure;
import Automata.Figures.OneConnection;
import Automata.Figures.ZeroConnection;
import CH.ifa.draw.figure.TextFigure;
import CH.ifa.draw.framework.Drawing;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.framework.FigureChangeEvent;
import CH.ifa.draw.framework.FigureChangeListener;

import javax.swing.*;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


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

/*
 * Figures are not directly connected to nodes
 * as they are part of the view, however nodes
 * have got reference to figures, so the best
 * (and efficient) way of finding a node from
 * the figure is using a hashtable.
 */
public class FSM_Model implements FigureChangeListener
{
	private static FSM_Node start;

	private TextFigure tape_storage;

	private FSM_Node current;

	private Map<Figure, FSM_Node> nodes;

	private Drawing drawing;

	private CurrentStateDecorator selected;

	/*
	 * Singleton design pattern.
	 * It is used because different classes
	 * and objects need to access the same data
	 * structure.
	 */
	public static final FSM_Model me = new FSM_Model();

	public FSM_Model()
	{
		start = null;
		tape_storage = null;
		current = null;
		selected = null;

		nodes = new HashMap<Figure, FSM_Node>();
	}

	public void insert(Figure key, FSM_Node node)
	{
		nodes.put(key, node);
		/* Add this as a listener to the figure*/
		key.addFigureChangeListener(this);
	}

	public void replace(Figure fold, Figure fnew)
	{
		FSM_Node node = nodes.remove(fold);
		node.setMyFigure(fnew);
		nodes.put(fnew, node);
	}

	public boolean connectOne(Figure start, Figure end)
	{
		FSM_Node nstart, nend;

		nstart = nodes.get(start);
		nend = nodes.get(end);

		nstart.setOne(nend);

		return true;
	}

	public boolean connectZero(Figure start, Figure end)
	{
		FSM_Node nstart, nend;

		nstart = nodes.get(start);
		nend = nodes.get(end);

		nstart.setZero(nend);

		return true;
	}

	public boolean isValid()
	{
		if(start == null)
			return false;

		Collection<FSM_Node> states = nodes.values();

		for(FSM_Node n: states)
			if(!n.isValid())
				return false;

		return true;
	}

	public void step()
	{
		String tape = tape_storage.getText();
		if(selected != null)
		{
			Figure f = selected.peelDecoration();
			drawing.replace(selected, f);
		}
		if(current == null)
		{
			current = start;
			System.out.printf("Starting. Tape: %s\nStart State: %s\n", tape, current);
		}

		int c = next_character();
		System.out.printf("Read: %d\n", c);
		switch(c)
		{
			case 1:
				current = current.getOne();
				System.out.printf("ActualState: %s\nTape: %s\n", current.getMyFigure(), tape);
				break;
			case 0:
				current = current.getZero();
				System.out.printf("ActualState: %s\nTape: %s\n", current.getMyFigure(), tape);
				break;
			case -1:
				current = null;
				break;
		}
		if(current != null)
		{
			Figure fcurrent = find_figure(current);
			if(fcurrent != null)
			{
				CurrentStateDecorator dec = new CurrentStateDecorator(fcurrent);
				drawing.replace(fcurrent, dec);
				System.out.printf("CurrentFigure: %s -> %s\n", fcurrent, dec);
				selected = dec;
			}
		}
		if(tape.length() == 0 && current != null)
		{
			tape_storage.invalidate();
			drawing.remove(tape_storage);
			tape_storage.changed();
			current = null;
		}
	}

	private Figure find_figure(FSM_Node node)
	{
		Enumeration<Figure> figures = drawing.figures();
		Figure current;

		while(figures.hasMoreElements())
		{
			current = figures.nextElement();
			if(current instanceof CountingFigure)
			{
				if(current == node.getMyFigure())
					return current;
			}
			else if(current instanceof AutomataDecorator)
			{
				if(((AutomataDecorator) current).getParent() == node.getMyFigure())
					return current;
			}
		}
		return null;
	}

	int next_character()
	{
		String tape = tape_storage.getText();
		if(tape.length() == 0)
			return -1;

		char c = tape.charAt(tape.length() - 1);
		tape = tape.substring(0, tape.length() - 1);
		tape_storage.setText(tape);

		if(c == '1')
			return 1;
		else if (c == '0')
			return 0;
		else
		{
			String text = String.format("'%c' is a invalid characrer!", c);
			JOptionPane.showMessageDialog(null, text, "Error!", JOptionPane.ERROR_MESSAGE);
			return -1;
		}
	}

	public void setTape_storage(TextFigure tape_storage)
	{
		this.tape_storage = tape_storage;

		current = null;
		if(selected != null)
		{
			Figure f = selected.peelDecoration();
			drawing.replace(selected, f);
		}
		Figure fcurrent = find_figure(start);
		if(fcurrent != null)
		{
			CurrentStateDecorator dec = new CurrentStateDecorator(fcurrent);
			drawing.replace(fcurrent, dec);
			System.out.printf("CurrentFigure: %s -> %s\n", fcurrent, dec);
			selected = dec;
		}
	}

	public TextFigure getTape_storage()
	{
		return tape_storage;
	}

	public static FSM_Model getInstance()
	{
		return me;
	}

	public FSM_Node getNode(Figure f)
	{
		return nodes.get(f);
	}

	public FSM_Node getStart()
	{
		return start;
	}

	public void setStart(FSM_Node start)
	{
		FSM_Model.start = start;
	}

	public void setDrawing(Drawing drawing)
	{
		this.drawing = drawing;
	}

	public void print_debug()
	{
		int i = 0;
		Collection<FSM_Node> my_nodes = nodes.values();


		for(FSM_Node n: my_nodes)
				System.out.printf("[%2d] - %s\n", i++, n);

		System.out.printf("StartState: %s\n", start);
		System.out.printf("Is model valid: %s\n", isValid());
	}

	/*
	 * FigureChangeListener methods.
	 * The only one used is figureRemoved
	 * to remove the node from the data structure.
	 */
	@Override
	public void figureChanged(FigureChangeEvent e)
	{
	}

	@Override
	public void figureInvalidated(FigureChangeEvent e)
	{
	}

	/*
	 * This method removes from the data structure the
	 * figure that was deleted from the drawing.
	 *
	 * When a connection is removed, its links are
	 * also removed as well
	 */
	@Override
	public void figureRemoved(FigureChangeEvent e)
	{
		Figure f = e.getFigure();
		if(f instanceof CountingFigure || f instanceof AutomataDecorator)
		{
			/* remove the start state */
			if(f instanceof StartStateDecorator)
				FSM_Model.getInstance().setStart(null);

			if(f instanceof AutomataDecorator)
				f = ((AutomataDecorator) f).getParent();

			nodes.remove(f);
		}
		else if(f instanceof OneConnection)
		{
			Figure start = ((OneConnection) f).startFigure();
			FSM_Node node = nodes.get(start);
			if(node != null)
				node.setOne(null);
		}
		else if(f instanceof ZeroConnection)
		{
			Figure start = ((ZeroConnection) f).startFigure();
			FSM_Node node = nodes.get(start);
			if(node != null)
				node.setZero(null);
		}
	}

	@Override
	public void figureRequestRemove(FigureChangeEvent e)
	{
	}

	@Override
	public void figureRequestUpdate(FigureChangeEvent e)
	{
	}
}
